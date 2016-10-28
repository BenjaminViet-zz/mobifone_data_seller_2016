package com.benluck.vms.mobifonedataseller.util.csv;

public interface ReportCsvInfo {
	public String[] getHeaders();
	public String[] getCsvFields();
    public int getCsvFieldDecimalRound(String field);
    public int getFactor(String field);
    public String getSymbol(String field);
}
