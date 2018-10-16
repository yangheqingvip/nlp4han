package com.lc.nlp4han.constituent.unlex;

import java.util.HashSet;

/**
 * @author 王宁
 * @version 创建时间：2018年10月12日 下午3:54:45 分裂语法
 * 
 */
public class GrammarSpliter
{
	public static void splitGrammar(Grammar oldG)
	{
		splitRule(oldG.bRules);
		splitRule(oldG.uRules);
		splitRule(oldG.lexicon.getPreRules());
		for (Tree<Annotation> tree : oldG.treeBank)
		{
			splitTreeAnnotation(tree);
		}
		oldG.nonterminalTable.getNumSubsymbolArr().replaceAll(e -> Short.valueOf((short) (e * 2)));
	}

	private static <T extends Rule> void splitRule(HashSet<T> rules)
	{
		for (T rule : rules)
		{
			rule.split();
		}
	}

	public static void splitTreeAnnotation(Tree<Annotation> tree)
	{
		if (tree == null)
			return;
		if (tree.isLeaf())
			return;
		tree.getLabel().setNumSubSymbol((short) (tree.getLabel().getNumSubSymbol() * 2));
		
		for (Tree<Annotation> child : tree.getChildren())
		{
			if (!child.isLeaf())
				splitTreeAnnotation(child);
		}
	}
}