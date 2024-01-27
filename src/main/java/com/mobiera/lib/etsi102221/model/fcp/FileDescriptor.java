package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class FileDescriptor extends FCPDataObject {

	public static enum FileType { WORKING_EF, INTERNAL_EF, DF_OR_ADF, RFU };
	
	public static enum FileStructure { NO_INFORMATION_GIVEN, TRANSPARENT, 
		LINEAR, CYCLIC, BER_TLV, RFU };
	
	private byte dataCoding;
	
	public byte getDataCoding() {
		return dataCoding;
	}


	public int getRecordLength() {
		return recordLength;
	}


	public int getNumberOfRecords() {
		return numberOfRecords;
	}


	public boolean isShareable() {
		return shareable;
	}


	public FileType getFileType() {
		return fileType;
	}


	public FileStructure getFileStructure() {
		return fileStructure;
	}


	private int recordLength;
	
	private int numberOfRecords;
	
	private boolean shareable;
	
	private FileType fileType;
	
	private FileStructure fileStructure;
	
	
	public static FileDescriptor parse(byte [] value) throws Etsi102221Exception {
		FileDescriptor fd = new FileDescriptor();
		
		if (value.length != 2 && value.length != 5)
			throw new Etsi102221Exception("Wrong File Descriptor length");
		
		fd.decodeFileDescriptorByte(value[0]);
		fd.dataCoding = value[1];
		
		if (value.length > 2) {
			fd.recordLength = value[3] + (value[2] << 8); 
			fd.numberOfRecords = value[4];
		}
		
		
		return fd;
	}
	
	
	private void decodeFileDescriptorByte(byte value) {
		this.shareable = (value & 0x40) != 0x40;
		
		byte fileTypeCode = (byte) ((value & 0x3F) >> 3);
		
		switch (fileTypeCode) {
			case 0: this.fileType = FileType.WORKING_EF; break;
			case 1: this.fileType = FileType.INTERNAL_EF; break;
			case 7: this.fileType = FileType.DF_OR_ADF; break;
			default: this.fileType = FileType.RFU;
		}
		
		byte fileStructureCode = (byte) (value & 0x07);
		
		switch (fileStructureCode) {
			case 0: this.fileStructure = FileStructure.NO_INFORMATION_GIVEN; break;
			case 1: this.fileStructure = FileStructure.TRANSPARENT; break;
			case 2: this.fileStructure = FileStructure.LINEAR; break;
			case 6: this.fileStructure = FileStructure.CYCLIC; break;
			default: this.fileStructure = FileStructure.RFU;
		}
		
		// special case: BER-TLV structure
		if ((fileStructureCode == 1) && (fileTypeCode == 7)) 
			this.fileStructure = FileStructure.BER_TLV;
		
	}
	
	private byte encodeFileDescriptorByte() {
		byte value = 0;
		
		value |= shareable ? 0x40 : 0x00;
		
		byte fileTypeCode = 0;
		switch(fileType) {
			case WORKING_EF: fileTypeCode = 0; break;
			case INTERNAL_EF: fileTypeCode = 1; break;
			case DF_OR_ADF: fileTypeCode = 7; break;
			default: fileTypeCode = 6; // Any RFU		
		}
		
		value |= (fileTypeCode << 3);
		
		byte fileStructureCode = 0;
		switch(fileStructure) {
			case NO_INFORMATION_GIVEN: fileStructureCode = 0; break;
			case TRANSPARENT: fileStructureCode = 1; break;
			case LINEAR: fileStructureCode = 2; break;
			case CYCLIC: fileStructureCode = 6; break;
			default: fileStructureCode = 3; // Any RFU		
		}
		
		value |= fileStructureCode;
		
		// TODO: special case: BER-TLV structure
		return value;
		
	}
	
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_FILE_DESCRIPTOR;
	}
	
	@Override
	public byte [] getValue() {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		bo.write(encodeFileDescriptorByte());
		bo.write(dataCoding);
		if (fileStructure == FileStructure.LINEAR || fileStructure == FileStructure.CYCLIC) {
			bo.write(0);
			bo.write(recordLength & 0xFF);
			//bo.write(numberOfRecords);				
		}
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		String output = "";
		output += "\n - Shareable: " + shareable;
		output += "\n - Data Coding: " + dataCoding;
		output += "\n - Type: " + fileType.name();
		output += "\n - Structure: " + fileStructure.name();
		if (fileStructure == FileStructure.CYCLIC || fileStructure == FileStructure.LINEAR) {
			output += "\n -  Record Length: " + recordLength;
			output += "\n -  Number of Records: " + numberOfRecords;
		}
			
		return output;
	}
	
	public static class Builder {

		protected boolean shareable;
		protected FileType fileType;
		protected FileStructure fileStructure;
		protected byte dataCoding;
		protected int recordLength;
		protected int numberOfRecords;
		
		public Builder() {
			recordLength = 0;
			numberOfRecords = 0;
			shareable = true;
			fileType = FileType.WORKING_EF;
			fileStructure = FileStructure.NO_INFORMATION_GIVEN;
			dataCoding = 0x21;
		}

		public Builder shareable(boolean value) {
			this.shareable = value;
			return this;
		}

		public Builder fileType(FileType value) {
			this.fileType = value;
			return this;
		}

		public Builder fileStructure(FileStructure value) {
			this.fileStructure = value;
			return this;
		}
		
		public Builder dataCoding(byte value) {
			this.dataCoding = value;
			return this;
		}
		
		public Builder recordLength(int value) {
			this.recordLength = value;
			return this;
		}
		
		public Builder numberOfRecords(int value) {
			this.numberOfRecords = value;
			return this;
		}
		
		public FileDescriptor build() {
			FileDescriptor output = new FileDescriptor();
			output.shareable = this.shareable;
			output.fileType = this.fileType;
			output.fileStructure = this.fileStructure;
			output.dataCoding = this.dataCoding;
			output.recordLength = this.recordLength;
			output.numberOfRecords = this.numberOfRecords;

			return output;
		}
	}
}

