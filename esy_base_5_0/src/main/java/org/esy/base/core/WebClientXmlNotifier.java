package org.esy.base.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esy.base.entity.pojo.IdentityXmlPojo;
import org.esy.base.entity.pojo.OrganizationXmlPojo;
import org.esy.base.entity.pojo.PersonXmlPojo;
import org.esy.base.entity.pojo.multiData.Data;
import org.esy.base.entity.pojo.multiData.Head;
import org.esy.base.entity.pojo.multiData.MultiMsg;
import org.esy.base.entity.pojo.multiData.Row;
import org.esy.base.entity.pojo.multiData.Table;
import org.esy.base.util.XmlUtil;
import org.esy.base.util.YESUtil;


/**
 * 把vo转换成xml格式String
 * 
 * @author WYF 2017-01-03
 *
 */
public class WebClientXmlNotifier extends WebClientNotifier {

	@Override
	public void addListener(Object object, Object... args) {

		// 弄一个xml
		MultiMsg msg = new MultiMsg();

		// 我来组成xml头部
		Head head = new Head();
		head.setId("UIM" + new SimpleDateFormat("yyyyMMdd").format(new Date())
				+ ((int) (Math.random() * (9999 - 1000 + 1)) + 1000));
		head.setSource("UIM");
		head.setTarget("OA");
		head.setMsgType("0");
		head.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		msg.setHead(head);

		// 我来组成xml屁股
		Data data = new Data();
		List<Table> tables = new ArrayList<Table>();
		data.setTables(tables);
		Table table = new Table();
		tables.add(table);
		msg.setData(data);

		// 调用webservice的方法
		String arg1 = "";

		// xml的具体信息
		if (args[0] instanceof OrganizationXmlPojo) {

			head.setName("组织");
			head.setSerName("syncOrg");

			OrganizationXmlPojo o = (OrganizationXmlPojo) args[0];

			table.setTablename("ORG");
			table.setUid("系统内码，新增时放空字符串");
			table.setOid("组织编号");
			table.setName("组织名称");
			table.setAbbreviated("组织简称");
			table.setIsGroup("是否分组组织");
			table.setEnable("状态");
			table.setMemo("备注");
			table.setPid("上级组织编号，根组织放空");
			table.setSeq("排序号");
			table.setLevel("层级");
			List<Row> rows = new ArrayList<Row>();
			table.setRows(rows);

			Row row = new Row();
			rows.add(row);
			row.setAction(YESUtil.toString(args[1]));
			row.setUid(o.getUuid());
			row.setOid(o.getOid());
			row.setName(o.getName());
			row.setAbbreviated(o.getAbbreviated());
			row.setIsGroup(o.getIsGroup());
			row.setEnable(o.getEnable());
			row.setMemo(o.getMemo());
			row.setPid(o.getPid());
			row.setSeq(o.getSeq());
			row.setLevel(o.getLevel());

			arg1 = "ORG" + YESUtil.toString(args[1]);

		} else if (args[0] instanceof PersonXmlPojo) {

			head.setName("人员");
			head.setSerName("syncPerson");

			PersonXmlPojo p = (PersonXmlPojo) args[0];

			// person
			table.setTablename("Person");
			table.setEid("企业编号");
			table.setUid("系统内码,新增时放空字符串");
			table.setEmployeeNo("职工号");
			table.setPid("人力资源ID");
			table.setCname("姓名");
			table.setPy("拼音简拼(自动生成)");
			table.setPingyin("拼音全拼(自动生成)");
			table.setBirthday("出生日期[yyyy-mm-dd]");
			table.setOfficePhone("办公电话号码");
			table.setMobilePhone("移动电话号码");
			table.setSex("性别(男M 女F)");
			table.setMail("电子邮件");
			table.setEnable("人员状态(true启用/false停用)");
			table.setSeq("人员排序号");
			table.setType("人员类型(0-临时人员 / 1-正式职工)");
			table.setMemo("备注");
			List<Row> rows = new ArrayList<Row>();
			table.setRows(rows);

			Row row = new Row();
			rows.add(row);
			row.setAction(YESUtil.toString(args[1]));
			row.setEid(p.getEid());
			row.setUid(p.getUid());
			row.setEmployeeNo(p.getEmployeeNo());
			row.setPid(p.getPid());
			row.setCname(p.getCname());
			row.setPy(p.getPy());
			row.setPingyin(p.getPinyin());
			row.setBirthday(p.getBirthday());
			row.setOfficePhone(p.getOfficePhone());
			row.setMobilePhone(p.getMobilePhone());
			row.setSex(p.getSex());
			row.setSeq(p.getSeq());
			row.setMail(p.getMail());
			row.setEnable(p.getEnable());
			row.setType(p.getType());
			row.setMemo(p.getMemo());

			arg1 = "Person" + YESUtil.toString(args[1]);

			// identity
			if (YESUtil.isNotEmpty(p.getIdentity())) {
				List<Table> identTables = new ArrayList<Table>();
				row.setTables(identTables);
				Table identTable = new Table();
				identTables.add(identTable);

				identTable.setTablename("Identity");
				identTable.setEid("企业编号");
				identTable.setPid("人力资源ID");
				identTable.setOid("组织编号");
				identTable.setPositionId("职务编号(30-公司领导/40-处级领导/45-副处级领导/50-正科级人员/55-副科级人员/60-职员/70-临时人员)");
				identTable.setPostId("岗位编号");
				identTable.setEnable("身份状态(true启用/ false停用)");
				identTable.setType("身份类型(01-普通)");
				identTable.setIsMain("主职(true/false)");
				identTable.setSeq("部门内排序号");
				identTable.setStartDate("身份启用日期[yyyy-MM-dd]");
				identTable.setToDate("身份停用日期[yyyy-MM-dd]");
				List<Row> identRows = new ArrayList<Row>();
				identTable.setRows(identRows);

				for (IdentityXmlPojo itx : p.getIdentity()) {
					Row itxRow = new Row();
					itxRow.setEid(itx.getEid());
					itxRow.setPid(itx.getPid());
					itxRow.setOid(itx.getOid());
					itxRow.setPositionId(itx.getPositionId());
					itxRow.setPostId(itx.getPostId());
					itxRow.setEnable(itx.getEnable());
					itxRow.setType(itx.getType());
					itxRow.setIsMain(itx.getIsMain());
					itxRow.setSeq(itx.getSeq());
					itxRow.setStartDate(itx.getStartDate());
					itxRow.setToDate(itx.getToDate());
					identRows.add(itxRow);
				}
			}
		}

		this.getEventHandler().addEvent(object, "run", XmlUtil.convertToXml(msg, "GB2312"), arg1);

	}

	@Override
	public void notifyX() {
		try {
			this.getEventHandler().notifyX();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
