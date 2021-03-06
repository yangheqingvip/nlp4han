package com.lc.nlp4han.dependency.tb;

import java.util.ArrayList;

import com.lc.nlp4han.dependency.DependencySample;
import com.lc.nlp4han.dependency.DependencyTree;

/**
 * 
 * 将解析来的conf解析为树和sample
 * 
 * @author 王宁
 */
public class DependencyTreeTBUtil
{

	/**
	 * @param conf
	 *            解析得到的弧的配置
	 * @return 句子对应的依存树
	 */
	public static DependencyTree getTree(Configuration conf, String[] word, String[] pos)
	{
		return getTree(conf.getArcs(), word, pos);
	}

	/**
	 * @param arcs
	 *            解析得到的弧的list
	 * @return 句子对应的依存树
	 */
	public static DependencyTree getTree(ArrayList<Arc> arcs, String[] word, String[] pos)
	{
		return new DependencyTree(getSample(arcs, word, pos));
	}

	/**
	 * @param arcs
	 *            解析得到的弧的list
	 * @return 句子对应的DependencySample实例
	 */
	public static DependencySample getSample(ArrayList<Arc> arcs, String[] word, String[] pos)
	{
		String[] words = word;
		String[] poses = pos;
		String[] dependency = new String[poses.length - 1];
		String[] dependencyWords = new String[poses.length - 1];
		String[] dependencyIndices = new String[poses.length - 1];
		for (int i = 0; i < words.length - 1; i++)
		{
			dependency[i] = "_null";
			dependencyWords[i] = "_null";
			dependencyIndices[i] = "-1";
		}
		
		for (Arc arc : arcs)
		{
			words[arc.getHead().getIndexOfWord()] = arc.getHead().getWord();
			words[arc.getDependent().getIndexOfWord()] = arc.getDependent().getWord();
			poses[arc.getHead().getIndexOfWord()] = arc.getHead().getPos();
			poses[arc.getDependent().getIndexOfWord()] = arc.getDependent().getPos();
			dependency[arc.getDependent().getIndexOfWord() - 1] = arc.getRelation();
			dependencyWords[arc.getDependent().getIndexOfWord() - 1] = arc.getHead().getWord();
			dependencyIndices[arc.getDependent().getIndexOfWord() - 1] = String.valueOf(arc.getHead().getIndexOfWord());
		}

		return new DependencySample(words, poses, dependency, dependencyWords, dependencyIndices);
	}

}
