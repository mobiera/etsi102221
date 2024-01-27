package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.java.sim.util.tlv.ISOUtil;
import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.FCPTemplate;
import com.mobiera.lib.etsi102221.model.SelectResponse;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor.FileStructure;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor.FileType;
import com.mobiera.lib.etsi102221.model.fcp.ProprietaryInformation;
import com.mobiera.lib.etsi102221.model.fcp.pi.UICCCharacteristics;

public class SIMSelectResponse extends SelectResponse {

	
	public static SIMSelectResponse parse(byte [] data) throws Etsi102221Exception {
		
		SIMSelectResponse sr = new SIMSelectResponse();
		
		if (data.length < 14)
			throw new Etsi102221Exception("Wrong length: first 14 bytes are mandatory. Raw Data: " + ISOUtil.hexString(data));
		
		
		int totalFileSize = (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
		int fileId = (data[4] & 0xFF)<< 8 | (data[5] & 0xFF);
		
		byte fileType = data[6];
		switch (fileType) {
			case 1: // MF
			case 2: // DF
				sr.fcp = new FCPTemplate.Builder()
					.fileIdentifier(fileId)
					.totalFileSize(totalFileSize)
					.fileDescriptor(new FileDescriptor.Builder()
							.fileType(FileType.DF_OR_ADF)
							.build())
					.proprietaryInformation(new ProprietaryInformation.Builder()
							.uiccCharacteristics(UICCCharacteristics.parse(new byte [] {data[13]}))
							.build())
					// TODO: Permissions, status					
					.build();
			
			break;				
		case 4:
			// EF
			FileStructure fs; 
			switch (data[13]) {
				case 0:
					// 	Transparent
					fs = FileStructure.TRANSPARENT;
				break;
				case 1:
					// Linear
					fs = FileStructure.LINEAR;
				break;
				case 3:
					// Cyclic
					fs = FileStructure.CYCLIC;
					break;
				default:
					fs = FileStructure.RFU;
			}
			
			sr.fcp = new FCPTemplate.Builder()
					.fileIdentifier(fileId)
					.fileSize(totalFileSize)
					.fileDescriptor(new FileDescriptor.Builder()
							.fileType(FileType.WORKING_EF)
							.fileStructure(fs)
							.recordLength(data[14])
							.numberOfRecords(totalFileSize / data[14])
							.build())
					// TODO: Permissions, status
					.build();			
			break;
			//
		default:
			// RFU
			break;

		}

		
		return sr;
	}
	
	
	@Override
	public String toString() {
		return "FCP: " + fcp.toString();
	}

}
