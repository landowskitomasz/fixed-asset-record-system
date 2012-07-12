package com.ewid.ewidmanagers.client.struct;

import java.io.Serializable;
import java.util.Date;

public class FixedAsset implements Serializable {

	private static final long serialVersionUID = 4125828618097696932L;

	private int id;
	private Equipment equipment;
	private Date purchaseDate;
	private Date firstUseDate;
	private PurchaseDocument purchaseDocument;
	private String description;
	private FixedAssetClassSymbol fixedAssetClassSymbol;
	private double initialValue;
	private double amortizationRate;
	private double deductionAmount;
	private double updatedInitialValue;
	private String updatedDeductionAmount;
	private Date liquidationDate;
	private String liquidationCouse;
	private Date creationDate;
	private User creationBy;
	private Date lastModify;
	private User modifyBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getFirstUseDate() {
		return firstUseDate;
	}
	public void setFirstUseDate(Date firstUseDate) {
		this.firstUseDate = firstUseDate;
	}
	public PurchaseDocument getPurchaseDocument() {
		return purchaseDocument;
	}
	public void setPurchaseDocument(PurchaseDocument purchaseDocument) {
		this.purchaseDocument = purchaseDocument;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FixedAssetClassSymbol getFixedAssetClassSymbol() {
		return fixedAssetClassSymbol;
	}
	public void setFixedAssetClassSymbol(FixedAssetClassSymbol fixedAssetClassSymbol) {
		this.fixedAssetClassSymbol = fixedAssetClassSymbol;
	}
	public double getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}
	public double getAmortizationRate() {
		return amortizationRate;
	}
	public void setAmortizationRate(double amortizationRate) {
		this.amortizationRate = amortizationRate;
	}
	public double getDeductionAmount() {
		return deductionAmount;
	}
	public void setDeductionAmount(double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}
	public double getUpdatedInitialValue() {
		return updatedInitialValue;
	}
	public void setUpdatedInitialValue(double updatedInitialValue) {
		this.updatedInitialValue = updatedInitialValue;
	}
	public String getUpdatedDeductionAmount() {
		return updatedDeductionAmount;
	}
	public void setUpdatedDeductionAmount(String updatedDeductionAmount) {
		this.updatedDeductionAmount = updatedDeductionAmount;
	}
	public Date getLiquidationDate() {
		return liquidationDate;
	}
	public void setLiquidationDate(Date liquidationDate) {
		this.liquidationDate = liquidationDate;
	}
	public String getLiquidationCouse() {
		return liquidationCouse;
	}
	public void setLiquidationCouse(String liquidationCouse) {
		this.liquidationCouse = liquidationCouse;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public User getCreationBy() {
		return creationBy;
	}
	public void setCreationBy(User creationBy) {
		this.creationBy = creationBy;
	}
	public Date getLastModify() {
		return lastModify;
	}
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	public User getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(User modifyBy) {
		this.modifyBy = modifyBy;
	}
	@Override
	public String toString() {
		return "FixedAsset [id=" + id + ", equipment=" + equipment
				+ ", purchaseDate=" + purchaseDate + ", firstUseDate="
				+ firstUseDate + ", purchaseDocument=" + purchaseDocument
				+ ", description=" + description + ", fixedAssetClassSymbol="
				+ fixedAssetClassSymbol + ", initialValue=" + initialValue
				+ ", amortizationRate=" + amortizationRate
				+ ", deductionAmount=" + deductionAmount
				+ ", updatedInitialValue=" + updatedInitialValue
				+ ", updatedDeductionAmount=" + updatedDeductionAmount
				+ ", liquidationDate=" + liquidationDate
				+ ", liquidationCouse=" + liquidationCouse + ", creationDate="
				+ creationDate + ", creationBy=" + creationBy + ", lastModify="
				+ lastModify + ", modifyBy=" + modifyBy + "]";
	}

}
