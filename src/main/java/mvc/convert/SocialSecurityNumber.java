package mvc.convert;

public class SocialSecurityNumber {
	
	private final String value;

	public SocialSecurityNumber(String value) {
		this.value = value;
	}
	
	@MaskFormat("###-##-###")
	public String getValue() {
		return value;
	}
	
	public static SocialSecurityNumber valueof(@MaskFormat("###-##-###")String value) {
		return new SocialSecurityNumber(value);
	}
	
	

}
