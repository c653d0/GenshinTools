package util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class InternetConnectHelper {
    fun httpConnectByGet(url:String, timeout:Int, headers: Map<String, String>):String{
        val result = StringBuffer()
        try {
            //创建连接
            val ul = URL(url)
            val connection = ul.openConnection() as HttpURLConnection
            //设置连接超时时间
            connection.connectTimeout = timeout
            //设置连接请求方式
            connection.requestMethod = "GET"

            for (header in headers) {
                connection.setRequestProperty(header.key,header.value)
            }

            //开始连接
            connection.connect()

            //获取响应数据
            if (connection.responseCode == 200) {
                //获取返回的数据
                val inputStream = connection.inputStream;
                if (inputStream != null) {
                    val br = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                    val stringList = br.readLines()
                    //将返回结果进行拼接
                    for (s in stringList) {
                        result.append(s)
                    }
                    br.close()
                }
                inputStream.close()
            }
            //关闭远程连接
            connection.disconnect()
        } catch (e: Exception) {
            println("Class: InternetConnectHelper;Method: httpConnectByGet occur error:$e")
        }
        //返回结果
        return result.toString()
    }

    fun httpConnectByPost(url:String, timeout:Int,args:Map<String,String>, headers: Map<String,String>):String{
        val result = StringBuffer()
        try {
            val ul = URL(url)
            val connection = ul.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = timeout
            connection.readTimeout = timeout
            connection.doOutput = true
            connection.doInput = true

            //headers
            for (header in headers) {
                connection.setRequestProperty(header.key,header.value)
            }

            //参数
            val param = StringBuffer()
            var isFirst = true

            param.append("{")
            for (arg in args) {
                if(isFirst){
                    isFirst=false
                }else{
                    param.append(",")
                }
                param.append("\"${arg.key}\":\"${arg.value}\"")
            }
            param.append("}")

            if(!param.equals("")){
                val os = connection.outputStream
                os.write(param.toString().toByteArray(Charsets.UTF_8))
                os.close()
            }

            connection.connect()

            if(connection.responseCode == 200){
                val inputStream = connection.inputStream
                if(inputStream!=null){
                    val br = BufferedReader(InputStreamReader(inputStream,"UTF-8"))
                    val stringList = br.readLines()
                    for (s in stringList) {
                        result.append(s)
                    }
                    br.close()
                }
                inputStream.close()
            }
            connection.disconnect()


        }catch (e:Exception){
            println("Class: InternetConnectHelper;Method: httpConnectByPost occur error:$e")
        }
        return result.toString()
    }
}