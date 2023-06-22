#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_openinapp_api_RetrofitHelper_getToken(JNIEnv *env, jobject thiz) {
    return (*env)-> NewStringUTF(env, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI");
}