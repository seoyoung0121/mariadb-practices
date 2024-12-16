package bookmall.type;

public enum StatusType {
	VERIFYING("입금확인중"), PREPARING("배송준비"), SHIPPING("배송중"), DONE("배송완료");
	
	private final String label;
	
	StatusType(String label){
		this.label=label;
	}

	public String getLabel() {
		return label;
	}
	
}
