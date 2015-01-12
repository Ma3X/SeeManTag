// «Правильный» html парсинг
// http://habrahabr.ru/blogs/android_development/115127/

package com.xpyct.ondatra;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class HtmlHelper {
	   TagNode rootNode;

	   //Конструктор
	   public HtmlHelper(URL htmlPage) throws IOException
	   {
	     //Создаём объект HtmlCleaner
	     HtmlCleaner cleaner = new HtmlCleaner();
	     //Загружаем html код сайта
	     rootNode = cleaner.clean(htmlPage);
	   }

	   List<TagNode> getLinksByClass(String CSSClassname)
	   {
	     List<TagNode> linkList = new ArrayList<TagNode>();

	     //Выбираем все ссылки
	     TagNode linkElements[] = rootNode.getElementsByName("a", true);
	     for (int i = 0; linkElements != null && i < linkElements.length; i++)
	     {
	       //получаем атрибут по имени
	       String classType = linkElements[i].getAttributeByName("class");
	       //если атрибут есть и он эквивалентен искомому, то добавляем в список
	       if (classType != null && classType.equals(CSSClassname))
	       {
	         linkList.add(linkElements[i]);
	       }
	     }

	     return linkList;
	   }
}
