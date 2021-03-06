package com.ingeint.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MRequest;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.Env;
import org.eevolution.model.X_HR_Attribute;
import org.eevolution.model.X_HR_Employee;
import org.eevolution.model.X_HR_Payroll;

import com.ingeint.base.CustomProcess;
import com.ingeint.model.MHTJobEndowment;
import com.ingeint.model.MHTPersonalRequisition;
import com.ingeint.model.MHTPersonalRequisitionLine;

import ve.net.dcs.model.X_HT_EndowmentEmployee;

public class CreateEmployee extends CustomProcess {
	
	String p_ContractType="";
	Timestamp p_StartDate=null;
	String p_HRegion = "";
	String p_email = "";
	BigDecimal p_salary = Env.ZERO;
	Boolean IsDirect = false;
	Integer pC_Job_ID = 0;
	String p_PayrollTimeType = "";
	BigDecimal p_PartialTimeQty;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null);
			else if (name.equals("ContractType")) 
				p_ContractType = para[i].getParameterAsString();
			else if (name.equals("StartDate"))
				p_StartDate = para[i].getParameterAsTimestamp();
			else if (name.equals("HR_Region"))
				p_HRegion = para[i].getParameterAsString();
			else if (name.equals("EMail"))
				p_email = para[i].getParameterAsString();
			else if (name.equals("Salary"))
				p_salary = para[i].getParameterAsBigDecimal();
			else if (name.equals("IsDirect"))
				IsDirect = para[i].getParameterAsBoolean();
			else if (name.equals("HR_Job_ID"))
				pC_Job_ID = para[i].getParameterAsInt();
			else if (name.equals("PayrollTimeType"))
				p_PayrollTimeType = para[i].getParameterAsString();
			else if (name.equals("PartialTimeQty"))
				p_PartialTimeQty = para[i].getParameterAsBigDecimal();
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		MBPartner partner = null;
		MHTPersonalRequisitionLine reqline = null;
		MHTPersonalRequisition requisition = null;
		
		if (!IsDirect) {
			MRequest req = new MRequest(getCtx(), getRecord_ID(), get_TrxName());
			reqline = new MHTPersonalRequisitionLine(getCtx(), req.get_ValueAsInt("HT_PersonalRequisitonLine_ID"), get_TrxName());
			requisition = new MHTPersonalRequisition(getCtx(), reqline.getHT_PersonalRequisiton_ID(), get_TrxName());
			partner = new MBPartner(getCtx(), reqline.getC_BPartner_ID(), get_TrxName());
			partner.set_ValueOfColumn("HT_PersonalRequisiton_ID", requisition.get_ID());
		} else {
			partner = new MBPartner(getCtx(), getRecord_ID(), get_TrxName());
		}
		
		partner.setIsEmployee(true);		
		partner.set_ValueOfColumn("ContractType", p_ContractType);
		partner.set_ValueOfColumn("IsHRProspect", false);
		partner.set_ValueOfColumn("StartDate", p_StartDate);
		partner.set_ValueOfColumn("PayrollTimeType", p_PayrollTimeType);
		partner.saveEx();
		
		MUser user = new MUser(partner);
		user.setEMail(p_email);
		user.saveEx();
		
		MHTJobEndowment[] je = null;
		
		if (IsDirect)
			je = MHTJobEndowment.getJobEndowment(getCtx(),pC_Job_ID, get_TrxName());
		else
			je = MHTJobEndowment.getJobEndowment(getCtx(), requisition.getHR_Job_ID(), get_TrxName());
		
		for (MHTJobEndowment jobe : je) {
			
			X_HT_EndowmentEmployee ee = new X_HT_EndowmentEmployee(getCtx(), 0, get_TrxName());
			ee.setC_BPartner_ID(partner.get_ID());
			ee.setHT_ArticleOfEndowment_ID(jobe.getHT_ArticleOfEndowment_ID());
			ee.setQty(1);
			ee.setAD_Org_ID(partner.getAD_Org_ID());
			ee.saveEx();			
		}	
		
		Integer SalaryConcept = MSysConfig.getIntValue("HR_SalaryConcept",0,requisition.getAD_Client_ID());
		
		if (SalaryConcept==0)
			throw new AdempiereException("Por favor configure el Salario de Concepto");
			
			X_HR_Attribute attribute = new X_HR_Attribute(getCtx(), 0, get_TrxName());
			
			attribute.setAD_Org_ID(partner.getAD_Org_ID());
			attribute.setHR_Concept_ID(SalaryConcept);
			attribute.setC_BPartner_ID(partner.get_ID());
			attribute.setValidFrom(p_StartDate);
			attribute.setColumnType("A");
			if (p_salary.signum()>0)
				attribute.setAmount(p_salary);
			else
				attribute.setAmount(requisition.getEstimatedSalary());
			attribute.set_ValueOfColumn("HR_Region", p_HRegion);
			attribute.saveEx();
			
			if (p_PayrollTimeType.equals("TP")) {
				
				Integer PartialTime_ID = MSysConfig.getIntValue("HR_PartialTimeConcept",0,partner.getAD_Client_ID());
				
				if (PartialTime_ID==0)
					throw new AdempiereException("Por favor configure el Sysconfig HR_PartialTimeConcept para su grupo empresarial.");
				
				Integer HR_WorkTime_ID = MSysConfig.getIntValue("HR_WorkTime",0,partner.getAD_Client_ID());
				
				if (HR_WorkTime_ID==0)
					throw new AdempiereException("Por favor configure el Sysconfig HR_WorkTime para su Grupo Empresarial. ");
								
				X_HR_Attribute attributepartial = new X_HR_Attribute(getCtx(), 0, get_TrxName());
				
				attributepartial.setAD_Org_ID(partner.getAD_Org_ID());
				attributepartial.setC_BPartner_ID(partner.get_ID());
				attributepartial.setHR_Concept_ID(PartialTime_ID);
				attributepartial.setValidFrom(p_StartDate);
				attributepartial.setColumnType("Q");
				attributepartial.setQty(p_PartialTimeQty);
				attributepartial.saveEx();
				
				X_HR_Attribute attributeWorkTime = new X_HR_Attribute(getCtx(), 0, get_TrxName());
				
				attributeWorkTime.setAD_Org_ID(partner.getAD_Org_ID());
				attributeWorkTime.setC_BPartner_ID(partner.get_ID());
				attributeWorkTime.setHR_Concept_ID(HR_WorkTime_ID);
				attributeWorkTime.setValidFrom(p_StartDate);
				attributeWorkTime.setColumnType("Q");
				attributeWorkTime.setQty(BigDecimal.valueOf(3));
				attributeWorkTime.saveEx();		
				
			}
				
		List<X_HR_Payroll> xpayrolls = new Query(getCtx(), X_HR_Payroll.Table_Name,
				"HR_Region IN (?,'AM') AND IsExcludeFromPersonalRequest = 'N' "
				+ "AND AD_Client_ID = ? ",
				get_TrxName())
				.setParameters(p_HRegion, getAD_Client_ID())
				.list();
		
		for (X_HR_Payroll xpayroll:xpayrolls) {
			X_HR_Employee employee = new X_HR_Employee(getCtx(), 0, get_TrxName());
			
			employee.setC_BPartner_ID(partner.get_ID());
			employee.setAD_Org_ID(partner.getAD_Org_ID());
			employee.setStartDate(p_StartDate);
			employee.setHR_Department_ID(requisition.getHR_Department_ID());
			employee.setHR_Job_ID(requisition.getHR_Job_ID());
			employee.set_ValueOfColumn("HR_Region",p_HRegion);
			employee.setHR_Payroll_ID(xpayroll.getHR_Payroll_ID());
			employee.saveEx();			
		}		
		return "Empleado creado";
	}
}
