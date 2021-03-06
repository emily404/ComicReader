package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class JoeAndMonkey extends IndexedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.joeandmonkey.com";
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://www.joeandmonkey.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("Previous");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*\'/","");
		final_str = final_str.replaceAll("\'.*","");
	   	int finalid = Integer.parseInt(final_str);
	   	finalid++;
	   	return finalid;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.joeandmonkey.com/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*com/", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String str2 = null;
		String final_str = null;
		String final_title = null;
		String final_date = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img class=\"centered\" src=\"");
			if (index1 != -1) {
				final_str = str;
				
				int index2 = str.indexOf("strong");
				while (index2 == -1) {
					str2 = reader.readLine();
					index2 = str2.indexOf("strong");
				}
				final_date = str2;
				}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_date = final_date.replaceAll(".*<strong>","");
		final_date = final_date.replaceAll("</strong>.*","");
		final_title=final_date;
		strip.setTitle("Joe And Monkey - "+final_title); 
		strip.setText("-NA-");
		return "http://www.joeandmonkey.com"+final_str;
	}
	
	@Override
	protected int getFirstId() {
		return 0;
	}
}
