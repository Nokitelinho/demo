/*
 * OracleSqlFormatter.java Created on 11-May-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.utils;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import SQLinForm_200.SQLForm;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			11-May-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
class OracleSqlFormatter {
	
	static String formatSql(String sql){
		SQLForm fmt = new SQLForm(new HeadlessDocument(sql.length()));
		fmt.setCase(false, false);
		fmt.setLowerCase(true);
		fmt.setGraphLevel(false);
		fmt.setSuppressSpace(true);
		fmt.setQuoteCharacter("\'");
		fmt.setdoubleIndentionMasterKeyword(true);
		fmt.setAndOrIndention(true);
		fmt.setNumCommas(5);
		fmt.setIndention(2, false);
		fmt.setSuppressEmptyLine(false);
		fmt.setLinebreakBeforeLineComment(false);
		fmt.setLinebreakBeforeConcat(true);
		fmt.setLinebreakAfterConcat(false);
		fmt.setLineBreak(false, true, true, false, false, false);
		fmt.setVariableName("SQL");
		fmt.setFormatLanguage("SQL");
		fmt.setOneLineSQL(false);
		fmt.setSourceSQL(false, "\"");
		fmt.setti("Only for Administrator");
		fmt.setSourceSQLLanguage("Oracle");
		fmt.setPageWidth(999);
		fmt.setColor(false);
		fmt.setLineNumber(false);
		fmt.setLinebreakKeyword(false);
		fmt.setLinebreakCase(true);
		fmt.setLinebreakCaseThen(false);
		fmt.setLinebreakCaseWhen(false);
		fmt.setLinebreakCaseAndOr(true);
		fmt.setLinebreakCaseElse(true);
		fmt.setLinebreakJoin(true);
		fmt.setLinebreakJoinJoin(false);
	    fmt.setLinebreakJoinOn(false);
	    fmt.setAlignmentEqual(true); //
	    fmt.setAlignmentOperator(false);
	    fmt.setAlignmentAs(false);
	    fmt.setAlignmentComma(false);
	    fmt.setAlignmentComment(true);
	    fmt.setAlignmentConcat(true);
	    fmt.setAlignmentDeclaration(true);
	    fmt.setAlignmentKeyword(false);
	    fmt.setSuppressComment(false);
	    fmt.setSuppressLinebreak(false);
	    fmt.setReplaceComment(false);
	    fmt.setSuppressEmptyLine(false);
	    fmt.setBracketSpaces("oneSpaceOutsideBracket");
	    fmt.setCommaSpaces("oneSpaceAfterComma");
	    fmt.setEqualSpaces("oneSpaceAroundEqual");
	    fmt.setSmallSQLWidth(80);
	    try {
			sql = fmt.formatSQLAsString(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return sql;
	}
	
	protected static class HeadlessDocument implements Document{
		//private Document delegate = new DefaultStyledDocument();
		private StringBuilder buf;
		
		public HeadlessDocument(int size){
			buf = new StringBuilder((int)Math.round(size * 1.25));
		}
		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getLength()
		 */
		@Override
		public int getLength() {
			//int ans = delegate.getLength();
			int ans2 = buf.length();
			/*if(ans != ans2)
				System.err.println("[HeadlessDocument] getLength : " + ans + " - " + ans2);*/
			return ans2;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#addDocumentListener(javax.swing.event.DocumentListener)
		 */
		@Override
		public void addDocumentListener(DocumentListener listener) {
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#removeDocumentListener(javax.swing.event.DocumentListener)
		 */
		@Override
		public void removeDocumentListener(DocumentListener listener) {
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#addUndoableEditListener(javax.swing.event.UndoableEditListener)
		 */
		@Override
		public void addUndoableEditListener(UndoableEditListener listener) {
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#removeUndoableEditListener(javax.swing.event.UndoableEditListener)
		 */
		@Override
		public void removeUndoableEditListener(UndoableEditListener listener) {
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getProperty(java.lang.Object)
		 */
		@Override
		public Object getProperty(Object key) {
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#putProperty(java.lang.Object, java.lang.Object)
		 */
		@Override
		public void putProperty(Object key, Object value) {
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#remove(int, int)
		 */
		@Override
		public void remove(int offs, int len) throws BadLocationException {
			//delegate.remove(offs, len);
			buf.delete(offs, offs + len);
			//System.out.println("[HeadlessDocument] remove : " + offs + ", " + len);
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		@Override
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			//delegate.insertString(offset, str, a);
			buf.insert(offset, str);
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getText(int, int)
		 */
		@Override
		public String getText(int offset, int length) throws BadLocationException {
			//String ans = delegate.getText(offset, length);
			String ans2 = buf.substring(offset, offset + length);
			/*if(!ans.equals(ans2))
				System.err.println("[HeadlessDocument] getText : " + ans + " - " + ans2);*/
			return ans2;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getText(int, int, javax.swing.text.Segment)
		 */
		@Override
		public void getText(int offset, int length, Segment txt) throws BadLocationException {
			/*delegate.getText(offset, length, txt);
			System.out.println("[HeadlessDocument] getText : " + offset + ", " + length + ", " + txt);*/
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getStartPosition()
		 */
		@Override
		public Position getStartPosition() {
			/*Position ans = delegate.getStartPosition();
			System.out.println("[HeadlessDocument] getStartPosition : " + ans);*/
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getEndPosition()
		 */
		@Override
		public Position getEndPosition() {
			/*Position ans = delegate.getEndPosition();
			System.out.println("[HeadlessDocument] getEndPosition : " + ans);*/
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#createPosition(int)
		 */
		@Override
		public Position createPosition(int offs) throws BadLocationException {
			/*Position ans = delegate.createPosition(offs);
			System.out.println("[HeadlessDocument] createPosition : " + offs);*/
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getRootElements()
		 */
		@Override
		public Element[] getRootElements() {
			/*Element[] ans = delegate.getRootElements();
			System.out.println("[HeadlessDocument] getRootElements : " + ans);*/
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#getDefaultRootElement()
		 */
		@Override
		public Element getDefaultRootElement() {
			/*Element ans = delegate.getDefaultRootElement();
			System.out.println("[HeadlessDocument] getDefaultRootElement : " + ans);*/
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Document#render(java.lang.Runnable)
		 */
		@Override
		public void render(Runnable r) {
			
		}
		
	}
}
