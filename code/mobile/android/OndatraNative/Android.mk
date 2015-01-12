LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := test_native
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := test_native.cpp
LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
### Load additional libs 
LOCAL_LDLIBS    := -llog -landroid -lEGL -lGLESv1_CM
### Link NativeActivity support
LOCAL_STATIC_LIBRARIES := android_native_app_glue

include $(BUILD_SHARED_LIBRARY)

$(call import-module,android/native_app_glue)
