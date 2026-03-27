package effectiveckotlin.ch1

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun countCharactersInFile(path: String): Int {
    val reader = BufferedReader(FileReader(path))

    // 전통적인 방법
//    try {
//        return reader.lineSequence().sumBy { it.length }
//    } finally {
//        reader.close()
//    }

    // use 사용
//    reader.use {
//        return reader.lineSequence().sumOf { it.length }
//    }

    // useLines 사용
    File(path).useLines { lines ->
        return lines.sumOf { it.length }
    }
}

// useLines 표현식
fun countCharactersInFile2(path: String): Int = File(path).useLines {
    lines -> lines.sumOf { it.length }
}

