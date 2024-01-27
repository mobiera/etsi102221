package com.mobiera.lib.etsi102221.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.Arrays;

import com.mobiera.java.sim.util.tlv.BerTlv;
import com.mobiera.java.sim.util.tlv.ISOUtil;
import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.fcp.CompactSecurityAttributes;
import com.mobiera.lib.etsi102221.model.fcp.DFName;
import com.mobiera.lib.etsi102221.model.fcp.FCPConstants;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor;
import com.mobiera.lib.etsi102221.model.fcp.FileIdentifier;
import com.mobiera.lib.etsi102221.model.fcp.FileSize;
import com.mobiera.lib.etsi102221.model.fcp.LifeCycleStatusInteger;
import com.mobiera.lib.etsi102221.model.fcp.PINStatusTemplateDO;
import com.mobiera.lib.etsi102221.model.fcp.ProprietaryInformation;
import com.mobiera.lib.etsi102221.model.fcp.ReferencedSecurityAttributes;
import com.mobiera.lib.etsi102221.model.fcp.SecurityAttributes;
import com.mobiera.lib.etsi102221.model.fcp.ShortFileIdentifier;
import com.mobiera.lib.etsi102221.model.fcp.TotalFileSize;

public class FCPTemplate {

	public FileDescriptor getFileDescriptor() {
		return fileDescriptor;
	}

	public FileSize getFileSize() {
		return fileSize;
	}

	public TotalFileSize getTotalFileSize() {
		return totalFileSize;
	}

	public DFName getDfName() {
		return dfName;
	}

	public FileIdentifier getFileIdentifier() {
		return fileIdentifier;
	}

	public ShortFileIdentifier getShortFileIdentifier() {
		return shortFileIdentifier;
	}

	public LifeCycleStatusInteger getLifeCycleStatusInteger() {
		return lifeCycleStatusInteger;
	}

	public SecurityAttributes getSecurityAttributes() {
		return securityAttributes;
	}

	public PINStatusTemplateDO getPinStatusTemplate() {
		return pinStatusTemplate;
	}

	public ProprietaryInformation getProprietaryInformation() {
		return proprietaryInformation;
	}

	public List<BerTlv> getUnprocessedTLVs() {
		return unprocessedTLVs;
	}
	
	private FileDescriptor fileDescriptor;
	private FileSize fileSize;
	private TotalFileSize totalFileSize;
	private DFName dfName;
	private FileIdentifier fileIdentifier;
	private ShortFileIdentifier shortFileIdentifier;
	private LifeCycleStatusInteger lifeCycleStatusInteger;
	private SecurityAttributes securityAttributes;
	private PINStatusTemplateDO pinStatusTemplate;
	private ProprietaryInformation proprietaryInformation;
	
	// Not recognized data (i.e. TLVs not supported)
	private List<BerTlv> unprocessedTLVs;
	
	public static FCPTemplate parse(byte [] data) throws Etsi102221Exception {
		
		FCPTemplate fcp = new FCPTemplate();
		BerTlv tlv;
		
		try {
			tlv = BerTlv.parse(data);	
		} catch(Exception e) {
			throw new Etsi102221Exception("Cannot parse BerTlv. Raw Data: " + ISOUtil.hexString(data));
		}
		

		if (tlv.getTag().getBytes()[0] != FCPConstants.FCP_TAG_FCP_TEMPLATE) 
			throw new Etsi102221Exception("Select Response does not contain FCP TEMPLATE");
		
		List<BerTlv> tlvs = BerTlv.parseList(tlv.getValue(), false);
		
		for (BerTlv t : tlvs) {
			byte tag = t.getTag().getBytes()[0];
			
			switch (tag) {
				case FCPConstants.FCP_TAG_DF_NAME: 
					fcp.dfName = DFName.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_FILE_DESCRIPTOR: 
					fcp.fileDescriptor = FileDescriptor.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_FILE_IDENTIFIER: 
					fcp.fileIdentifier = FileIdentifier.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_FILE_SIZE: 
					fcp.fileSize = FileSize.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_TOTAL_FILE_SIZE: 
					fcp.totalFileSize = TotalFileSize.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_LIFE_CYCLE_STATUS_INTEGER: 
					fcp.lifeCycleStatusInteger = LifeCycleStatusInteger.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_SHORT_FILE_IDENTIFIER: 
					fcp.shortFileIdentifier = ShortFileIdentifier.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_SECURITY_ATTRIBUTES_COMPACT: 
					fcp.securityAttributes = CompactSecurityAttributes.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_SECURITY_ATTRIBUTES_REFERENCED: 
					fcp.securityAttributes = ReferencedSecurityAttributes.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_PIN_STATUS_TEMPLATE_DO: 
					fcp.pinStatusTemplate = PINStatusTemplateDO.parse(t.getValue()); 
					break;
				case FCPConstants.FCP_TAG_PROPIETARY_INFORMATION: 
					fcp.proprietaryInformation = ProprietaryInformation.parse(t.getValue()); 
					break;

				default:
					fcp.unprocessedTLVs.add(t);
					//TODO
			}
			
		}
		
		return fcp;
	}
	
	public FCPTemplate(){
		unprocessedTLVs = new ArrayList<BerTlv>();
	}
	
	public byte [] getBytes() throws Etsi102221Exception, IOException {
		
		byte [] data = new byte[0];
		
		if (fileDescriptor != null)
			data = Arrays.concatenate(data, fileDescriptor.getBytes());
		if (fileIdentifier != null)
			data = Arrays.concatenate(data, fileIdentifier.getBytes());
		if (dfName != null)
			data = Arrays.concatenate(data, dfName.getBytes());
		if (lifeCycleStatusInteger != null)
			data = Arrays.concatenate(data, lifeCycleStatusInteger.getBytes());
		if (securityAttributes != null)
			data = Arrays.concatenate(data, securityAttributes.getBytes());
		if (fileSize != null)
			data = Arrays.concatenate(data, fileSize.getBytes());
		if (totalFileSize != null)
			data = Arrays.concatenate(data, totalFileSize.getBytes());
		if (pinStatusTemplate != null)
			data = Arrays.concatenate(data, pinStatusTemplate.getBytes());
		if (shortFileIdentifier != null)
			data = Arrays.concatenate(data, shortFileIdentifier.getBytes());
		if (proprietaryInformation != null)
			data = Arrays.concatenate(data, proprietaryInformation.getBytes());
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		bo.write(FCPConstants.FCP_TAG_FCP_TEMPLATE);
		bo.write(data.length);
		bo.write(data);
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		String output = "";
		
		if (fileDescriptor != null) output += "\nFile Descriptor : " + fileDescriptor.toString();
		if (fileSize != null) output += "\nFile Size : " + fileSize.toString();
		if (totalFileSize != null) output += "\nTotal File Size : " + totalFileSize.toString();
		if (dfName != null) output += "\nDF Name : " + dfName.toString();
		if (fileIdentifier != null) output += "\nFile Identifier : " + fileIdentifier.toString();
		if (shortFileIdentifier != null) output += "\nShort File Identifier : " + shortFileIdentifier.toString();
		if (lifeCycleStatusInteger != null) output += "\nLife Cycle Status : " + lifeCycleStatusInteger.toString();
		if (securityAttributes != null) output += "\nSecurity Attributes : " + securityAttributes.toString();
		if (pinStatusTemplate != null) output += "\nPIN Status : " + pinStatusTemplate.toString();
		if (proprietaryInformation != null) output += "\nProprietary Information : " + proprietaryInformation.toString();
		
		if (unprocessedTLVs.size() > 0) {
			output += "\nData not processed: ";
			for (BerTlv tlv : unprocessedTLVs){
				output += " " + tlv.toHexString();
			}
		}
		
		return output;
	}
	
	public static class Builder {

		protected FileDescriptor fileDescriptor;
		protected FileSize fileSize;
		protected TotalFileSize totalFileSize;
		protected DFName dfName;
		protected FileIdentifier fileIdentifier;
		protected ShortFileIdentifier shortFileIdentifier;
		protected LifeCycleStatusInteger lifeCycleStatusInteger;
		protected SecurityAttributes securityAttributes;
		protected PINStatusTemplateDO pinStatusTemplate;
		protected ProprietaryInformation proprietaryInformation;
		
		
		public Builder() {
		}
		
		public Builder fileDescriptor(FileDescriptor value) {
			this.fileDescriptor = value;
			return this;
		}

		public Builder fileSize(FileSize value) {
			this.fileSize = value;
			return this;
		}
		
		public Builder fileSize(int value) {
			this.fileSize = new FileSize.Builder()
					.allocatedDataBytes(value)
					.build();
			return this;
		}
		
		public Builder totalFileSize(TotalFileSize value) {
			this.totalFileSize = value;
			return this;
		}

		public Builder totalFileSize(int value) {
			this.totalFileSize = new TotalFileSize.Builder()
					.allocatedDataBytes(value)
					.build();
			return this;
		}

		public Builder dfName(DFName value) {
			this.dfName = value;
			return this;
		}
		
		public Builder fileIdentifier(FileIdentifier value) {
			this.fileIdentifier = value;
			return this;
		}
		
		public Builder fileIdentifier(int value) {
			this.fileIdentifier =  new FileIdentifier.Builder()
					.fid(value)
					.build();
			return this;
		}

		public Builder shortFileIdentifier(ShortFileIdentifier value) {
			this.shortFileIdentifier = value;
			return this;
		}

		public Builder lifeCycleStatusInteger(LifeCycleStatusInteger value) {
			this.lifeCycleStatusInteger = value;
			return this;
		}
		
		public Builder lifeCycleStatusInteger(LifeCycleStatusInteger.State value) {
			this.lifeCycleStatusInteger = new LifeCycleStatusInteger.Builder()
					.state(value)
					.build();
			return this;
		}
		
		
		public Builder securityAttributes(SecurityAttributes value) {
			this.securityAttributes = value;
			return this;
		}

		public Builder pinStatusTemplate(PINStatusTemplateDO value) {
			this.pinStatusTemplate = value;
			return this;
		}

		public Builder proprietaryInformation(ProprietaryInformation value) {
			this.proprietaryInformation = value;
			return this;
		}
		
		
		public FCPTemplate build() throws Etsi102221Exception {
			
			FCPTemplate output = new FCPTemplate();
		
			
			output.fileDescriptor = this.fileDescriptor;
			output.fileSize = this.fileSize;
			output.totalFileSize = this.totalFileSize;
			output.dfName = this.dfName;
			output.fileIdentifier = this.fileIdentifier;
			output.shortFileIdentifier = this.shortFileIdentifier;
			output.lifeCycleStatusInteger = this.lifeCycleStatusInteger;
			output.securityAttributes = this.securityAttributes;
			output.pinStatusTemplate = this.pinStatusTemplate;
			output.proprietaryInformation = this.proprietaryInformation;
			
			return output;
		}
	}
	
}
