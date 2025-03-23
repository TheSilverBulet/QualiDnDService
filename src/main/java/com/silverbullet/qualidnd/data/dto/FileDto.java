package com.silverbullet.qualidnd.data.dto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Class to represent a file
 *
 * @author Batman
 *
 */
public class FileDto {

	private String fileName;
	private String fileType;
	private byte[] fileData;

	/**
	 * Constructor
	 */
	public FileDto() {

	}

	/**
	 * Parameter Constructor
	 *
	 * @param name name of the file
	 */
	public FileDto(final String name) {
		this.fileName = name;
	}

	/**
	 * Getter for filename
	 *
	 * @return
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Setter for filename
	 *
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Getter for the fileType retrieved
	 *
	 * @return
	 */
	public String getFileType() {
		return this.fileType;
	}

	/**
	 * Setter for the fileType of the file
	 *
	 * @param fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * Getter for the file data as a byte array
	 *
	 * @return
	 */
	public byte[] getFileData() {
		return this.fileData;
	}

	/**
	 * Setter for the file data as a byte array
	 *
	 * @param fileData
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	/**
	 * Helper method to transform the byte array as an InputStream
	 * 
	 * @return
	 */
	public InputStream getFileDataAsStream() {
		return new ByteArrayInputStream(this.fileData);
	}

}
