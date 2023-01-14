package data

import java.lang.StringBuilder

data class AnalyseData(
    val fiveStarNum:Int,
    val fourStarNum:Int,
    val threeStarNum:Int,
    val fourStarCharacterNum:Int,
    val fourStarWeaponNum:Int,
    val fiveStarList:ArrayList<FinalWishData>,
    val total:Int,
    val gachaType:Int
)
{
    override fun toString(): String {
        if(total==0)
            return ""
        var str =StringBuilder()
        when(gachaType){
            GachaType.Character -> str.append("角色活动祈愿\n")
            GachaType.Weapon -> str.append("武器活动祈愿\n")
            GachaType.Permanent -> str.append("常驻祈愿\n")
            GachaType.FreshMan -> str.append("新手祈愿\n")
        }
        str.append("一共${total}抽\n" +
                "\t${threeStarNum}个三星\n" +
                "\t${fourStarNum}个四星\n" +
                "\t\t${fourStarCharacterNum}个角色，${fourStarCharacterNum}个武器\n" +
                "\t${fiveStarNum}个五星\n" +
                "\t\t"
        )
        var i=0
        for (data in fiveStarList) {
            i++
            str.append("${data.name}[${data.guarantee}] ")
            if(i%4==0)
                str.append("\n" +
                        "\t\t")
        }

        return str.toString()
    }
}