#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

// JNI_OnLoad надо попробовать написать!!!

JNIEXPORT jstring JNICALL Java_com_xpyct_ondatra_HtmlActivity_stringFromJNI(JNIEnv* env, jclass clazz);

#ifdef __cplusplus
}
#endif
