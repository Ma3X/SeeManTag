#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_example_markedone_myapp_FullscreenActivity_stringFromJNI(JNIEnv* env, jclass clazz);

#ifdef __cplusplus
}
#endif
