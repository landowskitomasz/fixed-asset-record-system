package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.FixedAssetClassSymbol;
import com.ewid.ewidmanagers.client.struct.PurchaseDocument;
import com.ewid.ewidmanagers.client.struct.Record;

public interface RecordManagerCommon {
	public List<Record> getRecords() throws EwidGetListException;
	public void addRecord(Record record) throws EwidAddException;
	public void editRecord(Record record) throws EwidEditException;
	public void deleteRecord(int recordId) throws EwidDeleteException;
	public Record getRecordById(int recordId) throws EwidGetObjectException;
	public List<FixedAssetClassSymbol> getFixedAssetClassSymbols() throws EwidGetListException;
	public List<PurchaseDocument> getPurchaseDocuments() throws EwidGetListException;
	public FixedAssetClassSymbol getFixedAssetClassSymbolsById(int fixedAssetClassSymbolId) throws EwidGetObjectException;
	public PurchaseDocument getPurchaseDocumentsById(int purchaseDocumentId) throws EwidGetObjectException;
}
