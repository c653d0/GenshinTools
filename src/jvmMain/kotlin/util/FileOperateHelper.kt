package util

import data.GachaType
import java.io.File
import java.nio.charset.Charset

class FileOperateHelper {
    private val workDirectory = "${System.getProperty("user.dir")}\\userData\\"

    fun writeToFile(uid:String, content:String){
        val directory = File(workDirectory)
        if(!directory.exists()){
            directory.mkdir()
        }

        val myFile = File("$workDirectory$uid.json")
        if(!myFile.exists()){
            myFile.createNewFile()
        }
        println(myFile.path)
        myFile.bufferedWriter().use { out->
            out.write(content)
        }
    }

    fun readFromFile(uid: String): String {
        val fileName = "$workDirectory$uid.json"
        val myFile = File(fileName)
        if (!myFile.exists()) {
            return "[GenshinTools]:File is not exist"
        }

        val ins = myFile.inputStream()

        return ins.readBytes().toString(Charset.defaultCharset())
    }
}