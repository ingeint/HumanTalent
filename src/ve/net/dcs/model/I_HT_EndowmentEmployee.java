/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package ve.net.dcs.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HT_EndowmentEmployee
 *  @author iDempiere (generated) 
 *  @version Release 1.0b
 */
@SuppressWarnings("all")
public interface I_HT_EndowmentEmployee 
{

    /** TableName=HT_EndowmentEmployee */
    public static final String Table_Name = "HT_EndowmentEmployee";

    /** AD_Table_ID=1000018 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/** Set Date Delivered.
	  * Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered);

	/** Get Date Delivered.
	  * Date when the product was delivered
	  */
	public Timestamp getDateDelivered();

    /** Column name DateExpiration */
    public static final String COLUMNNAME_DateExpiration = "DateExpiration";

	/** Set Expiration Date.
	  * Date of Expiration
	  */
	public void setDateExpiration (Timestamp DateExpiration);

	/** Get Expiration Date.
	  * Date of Expiration
	  */
	public Timestamp getDateExpiration();

    /** Column name HT_ArticleOfEndowment_ID */
    public static final String COLUMNNAME_HT_ArticleOfEndowment_ID = "HT_ArticleOfEndowment_ID";

	/** Set Article of Endowment	  */
	public void setHT_ArticleOfEndowment_ID (int HT_ArticleOfEndowment_ID);

	/** Get Article of Endowment	  */
	public int getHT_ArticleOfEndowment_ID();

	public ve.net.dcs.model.I_HT_ArticleOfEndowment getHT_ArticleOfEndowment() throws RuntimeException;

    /** Column name HT_EndowmentEmployee_ID */
    public static final String COLUMNNAME_HT_EndowmentEmployee_ID = "HT_EndowmentEmployee_ID";

	/** Set Endowment Employee	  */
	public void setHT_EndowmentEmployee_ID (int HT_EndowmentEmployee_ID);

	/** Get Endowment Employee	  */
	public int getHT_EndowmentEmployee_ID();

    /** Column name HT_EndowmentEmployee_UU */
    public static final String COLUMNNAME_HT_EndowmentEmployee_UU = "HT_EndowmentEmployee_UU";

	/** Set HT_EndowmentEmployee_UU	  */
	public void setHT_EndowmentEmployee_UU (String HT_EndowmentEmployee_UU);

	/** Get HT_EndowmentEmployee_UU	  */
	public String getHT_EndowmentEmployee_UU();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (int Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public int getQty();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
