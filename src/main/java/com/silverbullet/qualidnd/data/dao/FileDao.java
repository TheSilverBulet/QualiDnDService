package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.GET_FILE_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.FileDto;

/**
 * DAO class to handle DB operations relating to files
 *
 * @author Batman
 *
 */
@Repository
public class FileDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(FileDao.class);

	/**
	 * Method to retrieve a file from the DB
	 *
	 * @param fileName the name of the file
	 * @return the file as a FileDto
	 */
	public FileDto getFile(String fileName) {

		FileDto file = null;

		if (!fileName.endsWith(DDConstants.PDF_EXTENSION)) {
			fileName = String.format(DDConstants.STRING_SUB2, fileName, DDConstants.PDF_EXTENSION);
		}

		try {
			file = this.template.queryForObject(GET_FILE_QUERY, new Object[] { fileName },
					new BeanPropertyRowMapper<>(FileDto.class));

		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "getFile: ", PROCESS_EXCEPTION, dae.getMessage()));
			return null;
		}

		return file;
	}

	/**
	 * Method to upload a file to the DB
	 *
	 * @param file the org.springframework.web.multipart.MultipartFile
	 *             representation of a file
	 * @return true if success, else false
	 */
	public boolean uploadFile(MultipartFile file) {
		int success;
		String filename = file.getOriginalFilename();
		if (file == null || file.isEmpty()) {
			return false;
		}
		if (!filename.endsWith(DDConstants.PDF_EXTENSION)) {
			filename = String.format(DDConstants.STRING_SUB2, filename, DDConstants.PDF_EXTENSION);
		}
		try {
			success = this.template.update(DDConstants.UPLOAD_FILE_QUERY, filename, file.getInputStream());
		} catch (final DataAccessException | IOException dae) {
			LOG.error(String.format(STRING_SUB3, "uploadFile: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

}
