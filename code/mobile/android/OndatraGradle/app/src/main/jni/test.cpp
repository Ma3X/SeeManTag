#include <android/log.h>
#include <string>
#include "test.h"

// JNI_OnLoad надо попробовать написать!!!

JNIEXPORT jstring JNICALL Java_com_xpyct_ondatra_HtmlActivity_stringFromJNI(JNIEnv* env, jclass clazz)
{
    std::string tag("GREETING");
    std::string message("Hello from C++!");
    __android_log_print(ANDROID_LOG_INFO, tag.c_str(), "%s", message.c_str());
    std::string jniMessage("Hello from JNI!");
    return env->NewStringUTF(jniMessage.c_str());
}
