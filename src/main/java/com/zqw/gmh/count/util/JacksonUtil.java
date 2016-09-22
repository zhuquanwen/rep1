package com.zqw.gmh.count.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	 public static JavaType getCollectionType(ObjectMapper mapper,Class<?> collectionClass, Class<?>... elementClasses) {   
		  return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	 } 
}
