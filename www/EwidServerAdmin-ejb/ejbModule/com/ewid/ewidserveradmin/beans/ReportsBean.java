package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidmanagers.client.ejb.local.ReportsManagerLocal;
import com.ewid.ewidmanagers.client.struct.Report;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@Name("reportsBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class ReportsBean implements Serializable {

	private static final long serialVersionUID = -6981634330769504921L;
	
	private Logger logger = Logger.getLogger(ReportsBean.class);
	
	@Manager
	ReportsManagerLocal reportsManager;
	
	private List<Report> fakeReport;
	
	private List<Report> lastMonthReport;
	private List<Report> lastYearReport;
	private List<Report> summaryReport;
	
	@Create
	public void onCreate() {
		logger.info("ReportsBean.onCreate");
		
		fakeReport = new ArrayList<Report>();
		lastMonthReport = new ArrayList<Report>();
		lastYearReport = new ArrayList<Report>();
		summaryReport = new ArrayList<Report>();
		
		retrieveLastMonthReport();
		retrieveLastYearReport();
		retrieveSummaryReport();
	}
	
	private void retrieveLastMonthReport() {
		logger.info("ReportsBean.retrieveLastMonthReport");
		lastMonthReport.add(reportsManager.getLastMonthReport());
		
	}
	
	private void retrieveLastYearReport() {
		logger.info("ReportsBean.retrieveLastYearReport");
		lastYearReport.add(reportsManager.getLastYearReport());
		
	}
	
	private void retrieveSummaryReport() {
		logger.info("ReportsBean.retrieveSummaryReport");
		summaryReport.add(reportsManager.getSummaryReport());
	}

	public List<Report> getLastMonthReport() {
		return lastMonthReport;
	}

	public void setLastMonthReport(List<Report> lastMonthReport) {
		this.lastMonthReport = lastMonthReport;
	}

	public List<Report> getLastYearReport() {
		return lastYearReport;
	}

	public void setLastYearReport(List<Report> lastYearReport) {
		this.lastYearReport = lastYearReport;
	}

	public List<Report> getSummaryReport() {
		return summaryReport;
	}

	public void setSummaryReport(List<Report> summaryReport) {
		this.summaryReport = summaryReport;
	}

	public List<Report> getFakeReport() {
		return fakeReport;
	}

	public void setFakeReport(List<Report> fakeReport) {
		this.fakeReport = fakeReport;
	}
}
