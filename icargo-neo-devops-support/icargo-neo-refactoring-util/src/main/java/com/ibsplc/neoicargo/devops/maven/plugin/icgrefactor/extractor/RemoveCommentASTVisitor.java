package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * The Class RefactorReturnStatementASTVisitor.
 */
public class RemoveCommentASTVisitor extends ASTVisitor {

    /** The document. */
    private final IDocument document;

    /** The rewrite. */
    //private final ASTRewrite rewrite;

    /**
     * Instantiates a new removes the comment ast visitor.
     *
     * @param document the document
     * @param rewrite the rewrite
     */
    //, ASTRewrite rewrite
    public RemoveCommentASTVisitor(IDocument document) {
        this.document = document;
        //this.rewrite = rewrite;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.LineComment)
     */
    @Override
    public boolean visit(LineComment node) {
    	try {
            // try to delete comment node
            // using ASTRewrite to record modification
            node.getAlternateRoot().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.visit(node);
    }
    
    @Override
    public boolean visit(BlockComment node) {
        try {
            // try to delete comment node
            // using ASTRewrite to record modification
            node.getAlternateRoot().delete();
            node.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.visit(node);
    }
}

