package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class CompactSecurityAttributes extends SecurityAttributes {

	public static class SecurityConditionByte {
		
		private int seid;

		boolean secureMessaging;
		boolean externalAuthentication;
		boolean userAuthentication;
		
		boolean allConditionsRequired;
		
		public static SecurityConditionByte decode(byte value) {
			SecurityConditionByte sc = new SecurityConditionByte();
			
			sc.seid = value & 0x0F;
			sc.allConditionsRequired = (value & 0x80) != 0;
			sc.secureMessaging = (value & 0x40) != 0;
			sc.externalAuthentication = (value & 0x20) != 0;
			sc.userAuthentication = (value & 0x10) != 0;
			
			return sc;
		}
		
		public static byte encode(SecurityConditionByte sc) {
			byte output = 0;
			
			output |= (sc.seid & 0x0F);
			output |= sc.allConditionsRequired ? 0x80 :0x00;
			output |= sc.secureMessaging ? 0x40 :0x00;
			output |= sc.externalAuthentication ? 0x20 :0x00;
			output |= sc.userAuthentication ? 0x10 :0x00;
			
			return output;
		}
		
		public static class Builder {

			protected int seid;

			protected boolean secureMessaging;
			protected boolean externalAuthentication;
			protected boolean userAuthentication;
			
			protected boolean allConditionsRequired;
			
			public Builder() {
			}

			public Builder seid(int value) {
				this.seid = value;
				return this;
			}

			public Builder secureMessaging(boolean value) {
				this.secureMessaging = value;
				return this;
			}

			public Builder externalAuthentication(boolean value) {
				this.externalAuthentication = value;
				return this;
			}
			
			public Builder userAuthentication(boolean value) {
				this.userAuthentication = value;
				return this;
			}

			public Builder allConditionsRequired(boolean value) {
				this.allConditionsRequired = value;
				return this;
			}
			
			public SecurityConditionByte build() {
				SecurityConditionByte output = new SecurityConditionByte();
				output.allConditionsRequired = this.allConditionsRequired;
				output.secureMessaging = this.secureMessaging;
				output.externalAuthentication = this.externalAuthentication;
				output.userAuthentication = this.userAuthentication;
				output.seid = this.seid;
				
				return output;
			}
		}
	}; 

	public SecurityConditionByte getDelete() {
		return delete;
	}


	public SecurityConditionByte getTerminate() {
		return terminate;
	}


	public SecurityConditionByte getActivate() {
		return activate;
	}


	public SecurityConditionByte getDeactivate() {
		return deactivate;
	}


	public SecurityConditionByte getWrite() {
		return write;
	}


	public SecurityConditionByte getUpdate() {
		return update;
	}


	public SecurityConditionByte getRead() {
		return read;
	}


	public SecurityConditionByte getCreateDF() {
		return createDF;
	}


	public SecurityConditionByte getCreateEF() {
		return createEF;
	}


	public SecurityConditionByte getDeleteChild() {
		return deleteChild;
	}


	/* SC for both EF and DF */
	private SecurityConditionByte delete;
	private SecurityConditionByte terminate;
	private SecurityConditionByte activate;
	private SecurityConditionByte deactivate;

	/* DF Specific */
	private SecurityConditionByte write;
	private SecurityConditionByte update;
	private SecurityConditionByte read;
	
	/* EF Specific */
	private SecurityConditionByte createDF;
	private SecurityConditionByte createEF;
	private SecurityConditionByte deleteChild;
	
	public static CompactSecurityAttributes parse(byte [] value) throws Etsi102221Exception {
		CompactSecurityAttributes fd = new CompactSecurityAttributes();

		// TODO: Handle Proprietary bit (0x80)
		
		byte am = value[0];
		
		int offset = 1;
		
		if ((am & 0x40) != 0) fd.delete = SecurityConditionByte.decode(value[offset++]);
		if ((am & 0x20) != 0) fd.terminate = SecurityConditionByte.decode(value[offset++]);
		if ((am & 0x10) != 0) fd.activate = SecurityConditionByte.decode(value[offset++]);
		if ((am & 0x08) != 0) fd.deactivate = SecurityConditionByte.decode(value[offset++]);
		
		if ((am & 0x04) != 0) {
			fd.write = SecurityConditionByte.decode(value[offset++]);
			fd.createDF = SecurityConditionByte.decode(value[offset]);
		}
		
		if ((am & 0x02) != 0) {
			fd.update = SecurityConditionByte.decode(value[offset++]);
			fd.createEF = SecurityConditionByte.decode(value[offset]);
		}
		
		if ((am & 0x01) != 0) {
			fd.read = SecurityConditionByte.decode(value[offset++]);
			fd.deleteChild = SecurityConditionByte.decode(value[offset]);
		}

		return fd;
	}
	
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_SECURITY_ATTRIBUTES_COMPACT;
	}
	
	@Override
	public byte [] getValue() {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		byte am = 0;
		if (delete != null) am |= 0x40;
		if (terminate != null) am |= 0x20;
		if (activate != null) am |= 0x10;
		if (deactivate != null) am |= 0x08;

		if ((write != null) || (createDF != null)) am |= 0x04;
		if ((update != null) || (createEF != null)) am |= 0x02;
		if ((read != null) || (deleteChild != null)) am |= 0x01;
		
		bo.write(am);
		
		if (delete != null) bo.write(SecurityConditionByte.encode(delete));
		if (terminate != null) bo.write(SecurityConditionByte.encode(terminate));
		if (activate != null) bo.write(SecurityConditionByte.encode(activate));
		if (deactivate != null) bo.write(SecurityConditionByte.encode(deactivate));

		if (write != null) bo.write(SecurityConditionByte.encode(write));
		else if (createDF != null) bo.write(SecurityConditionByte.encode(createDF));
		
		if (update != null) bo.write(SecurityConditionByte.encode(update));
		else if (createEF != null) bo.write(SecurityConditionByte.encode(createEF));
		if (read != null) bo.write(SecurityConditionByte.encode(read));
		else if (deleteChild != null) bo.write(SecurityConditionByte.encode(deleteChild));
		
		return bo.toByteArray();
	}
	
	
	public static class Builder {

		/* SC for both EF and DF */
		protected SecurityConditionByte delete;
		protected SecurityConditionByte terminate;
		protected SecurityConditionByte activate;
		protected SecurityConditionByte deactivate;

		/* DF Specific */
		protected SecurityConditionByte write;
		protected SecurityConditionByte update;
		protected SecurityConditionByte read;
		
		/* EF Specific */
		protected SecurityConditionByte createDF;
		protected SecurityConditionByte createEF;
		protected SecurityConditionByte deleteChild;
		
		public Builder() {
			// Default: READ and UPDATE always
			read = SecurityConditionByte.decode((byte) 0);
			update = SecurityConditionByte.decode((byte) 0);
		}

		public Builder delete(SecurityConditionByte value) {
			this.delete = value;
			return this;
		}

		public Builder terminate(SecurityConditionByte value) {
			this.terminate = value;
			return this;
		}

		public Builder activate(SecurityConditionByte value) {
			this.activate = value;
			return this;
		}

		public Builder deactivate(SecurityConditionByte value) {
			this.deactivate = value;
			return this;
		}

		public Builder write(SecurityConditionByte value) {
			this.write = value;
			return this;
		}

		public Builder update(SecurityConditionByte value) {
			this.update = value;
			return this;
		}

		public Builder read(SecurityConditionByte value) {
			this.read = value;
			return this;
		}

		public Builder createDF(SecurityConditionByte value) {
			this.createDF = value;
			return this;
		}

		public Builder createEF(SecurityConditionByte value) {
			this.createEF = value;
			return this;
		}

		public Builder deleteChild(SecurityConditionByte value) {
			this.deleteChild = value;
			return this;
		}
		
		public CompactSecurityAttributes build() {
			CompactSecurityAttributes output = new CompactSecurityAttributes();
			output.delete = this.delete;
			output.terminate = this.terminate;
			output.activate = this.activate;
			output.deactivate = this.deactivate;
			output.write = this.write;
			output.update = this.update;
			output.read = this.read;
			output.createDF = this.createDF;
			output.createEF = this.createEF;
			output.deleteChild = this.deleteChild;

			return output;
		}
	}

	@Override
	public Format getFormat() {
		return Format.COMPACT;
	}
}

