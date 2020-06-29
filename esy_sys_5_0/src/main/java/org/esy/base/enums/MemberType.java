package org.esy.base.enums;
/**
 * @author qiongwei.cai
 * @version 创建时间：2016年1月19日 上午11:48:53
 * groupMember,appAuthority等entity的type字段用到的所有的可能的关联值
 */
public enum MemberType {
	Account("A"), AccountType("AT"), Enterprise("E"), EnterpriseType("ET"), 
		Organization("O"), Position("PN"), Post("PT"), Person("P"), Group("G");
	
	private final String code;
	
	@Override
    public String toString() {
      return this.code;
    }
	
	private MemberType(String code) {
	     this.code = code;
	}
	
	
}
