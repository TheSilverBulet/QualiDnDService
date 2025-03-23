package com.silverbullet.qualidnd.controller;

import static com.silverbullet.qualidnd.common.DDConstants.PDF_DATA_TYPE;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB2;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.data.dto.FileDto;
import com.silverbullet.qualidnd.response.QDDResponse;

/**
 * Controller class to handle functionality relating to Files
 * 
 * @author Batman
 *
 */
@QDDController
public class FileController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(FileController.class);

	/**
	 * Method to serve file to client
	 *
	 * @param fileName The base name of the file to serve
	 * @return The file requested if it exists
	 */
	@GetMapping(value = "/serveReferenceFile/{fileName}")
	public ResponseEntity<InputStreamResource> serveFile(@PathVariable("fileName") String fileName) {
		if (StringUtils.isNotBlank(fileName) && Arrays.asList(referenceFileList).contains(fileName)) {
			final FileDto file = this.fileDao.getFile(fileName);
			if (file == null) {
				return ResponseEntity.badRequest().body(null);
			}
			try {
				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.parseMediaType(PDF_DATA_TYPE));
				headers.add("Content-Disposition", "inline");
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				// change back to
				// headers.setContentLength(file.getFileDataAsStream().readAllBytes().length);
				// once issue is resolved where Heroku won't compile with JDK 11
				final int lengthOfBytes = IOUtils.toByteArray(file.getFileDataAsStream()).length;
				headers.setContentLength(lengthOfBytes);
				return new ResponseEntity<>(new InputStreamResource(file.getFileDataAsStream()), headers,
						HttpStatus.OK);
			} catch (final IOException e) {
				LOG.error(String.format(STRING_SUB2, "ERROR with file ", e.getMessage()));
			}

		}
		LOG.warn("Filename was either blank or did not match Master List");
		return ResponseEntity.badRequest().body(null);
	}

	/**
	 * Method to upload a file to the database
	 *
	 * @param file file from the request
	 * @return Response to the upload
	 */
	@PostMapping("/uploadFile")
	public ResponseEntity<QDDResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		if (this.fileDao.uploadFile(file)) {
			return ResponseEntity.ok().body(new QDDResponse("Uploaded", true));
		}
		return ResponseEntity.badRequest().body(new QDDResponse("Failed", false));
	}
}
