package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.mobiera.java.sim.util.tlv.BerTlv;

public class ExpandedSecurityAttributes extends SecurityAttributes {


	public static class SecurityRule {
	
		
	}
	
	public static ExpandedSecurityAttributes parse(byte [] value) throws IOException {
		
		ExpandedSecurityAttributes fd = new ExpandedSecurityAttributes();
		
		// It is a TLV parsing but TLV order is important, therefore
		// we'll parse it in the hard way
		int offset = 0;
		
		List<BerTlv> tlvs = BerTlv.parseList(value, false);

		for (int i = 0; i < tlvs.size(); i++) {
			byte tag = tlvs.get(i).getTag().getBytes()[0];

			// Tags 8X have different meaning
			if (tag == 0x80) {
				
			}
			/*switch (tag) {
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
			}*/
		}

		return fd;
	}
	
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_SECURITY_ATTRIBUTES_EXPANDED;
	}
	
	@Override
	public byte [] getValue() {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		// TODO
		
		return bo.toByteArray();
	}
	
	@Override
	public Format getFormat() {
		return Format.EXPANDED;
	}

	
	public static class Builder {
		// TODO
		public Builder() {
		}

		public ExpandedSecurityAttributes build() {
			ExpandedSecurityAttributes output = new ExpandedSecurityAttributes();
		
			return output;
		}
	}
}

