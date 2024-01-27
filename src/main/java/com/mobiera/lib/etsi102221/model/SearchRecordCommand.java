package com.mobiera.lib.etsi102221.model;

import org.bouncycastle.util.Arrays;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class SearchRecordCommand extends UICCCommand {
	
	public static enum SearchMode { SIMPLE_FORWARD, SIMPLE_BACKWARD,
		ENHANCED_FORWARD, ENHANCED_BACKWARD, PROPRIETARY};

	protected Integer sfi;
	protected SearchMode searchMode;
	protected int recordNumber;
	protected byte [] searchString;

	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_SEARCH_RECORD;
	}

	public static class Builder {

		protected Integer sfi;
		protected SearchMode searchMode;
		protected int recordNumber;
		protected Integer fromOffsetInRecord;
		protected Integer length;
		protected byte [] searchString;
		
		
		public Builder() {
			// Default: read all contents until end of file
			searchMode = SearchMode.SIMPLE_FORWARD;
			length = 0;
		}
		
		public Builder sfi(int value) {
			this.sfi = value;
			return this;
		}

		public Builder recordNumber(int value) {
			this.recordNumber = value;
			return this;
		}

		public Builder length(int value) {
			this.length = value;
			return this;
		}

		public Builder fromOffsetInRecord(int value) {
			this.fromOffsetInRecord = value;
			return this;
		}
		
		public Builder searchMode(SearchMode value) {
			this.searchMode = value;
			return this;
		}

		public Builder searchString(byte [] value) {
			this.searchString = value;
			return this;
		}

		public SearchRecordCommand build() throws Etsi102221Exception {
			SearchRecordCommand output = new SearchRecordCommand();
			output.sfi = this.sfi;
			output.recordNumber = this.recordNumber;
			output.searchMode = this.searchMode;
			output.searchString = this.searchString;
			
			output.le = this.length;
			
			switch (searchMode) {
				case SIMPLE_FORWARD:
					output.p1 = recordNumber;
					output.p2 |= 0x04;
					output.data = this.searchString;
					break;
				case SIMPLE_BACKWARD:
					output.p1 = recordNumber;
					output.p2 |= 0x05;
					break;
				case ENHANCED_FORWARD:
					output.p1 = recordNumber;
					output.p2 |= 0x06;
					
					byte b1 = 0x04;
					byte b2 = 0;
					if (fromOffsetInRecord != null)
						b2 = fromOffsetInRecord.byteValue();
					
					output.data = new byte [] { b1, b2 };
					output.data = Arrays.concatenate(output.data, this.searchString);
					
					break;
					
				case ENHANCED_BACKWARD:
					output.p1 = recordNumber;
					output.p2 |= 0x06;
					
					b1 = 0x05;
					b2 = 0;
					if (fromOffsetInRecord != null)
						b2 = fromOffsetInRecord.byteValue();
					
					output.data = new byte [] { b1, b2 };
					output.data = Arrays.concatenate(output.data, this.searchString);
					
					break;
					
				case PROPRIETARY:
					output.p1 = recordNumber;
					output.p2 |= 0x07;
					output.data = this.searchString;
			}
			
			if (sfi != null)
				output.p1 |= sfi << 3;
			
			return output;
		}
	}
}
