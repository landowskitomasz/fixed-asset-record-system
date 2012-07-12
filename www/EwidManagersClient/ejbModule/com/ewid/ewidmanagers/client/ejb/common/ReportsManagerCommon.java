package com.ewid.ewidmanagers.client.ejb.common;

import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Report;

public interface ReportsManagerCommon {
	public Report getLastMonthReport() throws EwidGetObjectException;
	public Report getLastYearReport() throws EwidGetObjectException;
	public Report getSummaryReport() throws EwidGetObjectException;
}
