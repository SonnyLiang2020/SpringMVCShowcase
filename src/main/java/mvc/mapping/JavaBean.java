package mvc.mapping;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	//������ java���� <---> xml�ļ�   ֮���ת��
public class JavaBean {
	
	private String foo = "bar in mapping for self-learn...";
	
	private String fruit = "apple in mapping package...";

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	@Override
	public String toString() {
		return "JavaBean [foo=" + foo + ", fruit=" + fruit + "]";
	}

}
