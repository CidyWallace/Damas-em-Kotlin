package org.example


fun main() {
    var win = false
    var turn = true
    val playerBranco = "⚪"
    val playerPreto = "⚫"
    val casaBranca = " "
    val casaPreta = " "
    val board: Array<Array<String>> = buildBoard(playerBranco, playerPreto, casaBranca, casaPreta)

    while (!win){
        showBoard(board)
        if(turn){
            println("$playerBranco Turn")
            verificaMovimento(board, playerBranco, casaBranca)
            turn = false
        }else{
            println("$playerPreto Turn")
            verificaMovimento(board, playerPreto, casaBranca)
            turn = true
        }
    }
}

fun verificaMovimento(board: Array<Array<String>>, mainPlayer: String, casaBranca: String): Boolean {
    val secondPlayer = if(mainPlayer == "⚪") "⚫" else "⚪"
    var isEnemy = false
    val movimentacao: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )
    val enemyPos: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )
    val posicoes = posicao(board, mainPlayer)

    if(mainPlayer == "⚪"){
        //    Esquerda com oponente
        if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()+1][posicoes.second.toInt()-1] == secondPlayer){
            if(posicoes.first.toInt() + 2 <= 7 && posicoes.second.toInt() -2 >= 0 && board[posicoes.first.toInt()+2][posicoes.second.toInt()-2] == casaBranca){
                enemyPos["esquerda"] = posicoes.first.toInt()+1 to posicoes.second.toInt() - 1
                movimentacao["esquerda"] =posicoes.first.toInt()+2 to posicoes.second.toInt() - 2
                isEnemy = true
            }
//    Esquerda sem oponente
        }else if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()+1][posicoes.second.toInt()-1] == casaBranca){
            movimentacao["esquerda"] = posicoes.first.toInt()+1 to posicoes.second.toInt()-1
        }

//    Direita com oponente
        if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()+1][posicoes.second.toInt()+1] == secondPlayer){
            if(posicoes.first.toInt() + 2 <= 7 && posicoes.second.toInt() + 2 <= 7 && board[posicoes.first.toInt()+2][posicoes.second.toInt()+2] == casaBranca){
                enemyPos["direita"] = posicoes.first.toInt()+1 to posicoes.second.toInt() + 1
                movimentacao["direita"] = posicoes.first.toInt()+2 to posicoes.second.toInt()+2
                isEnemy = true
            }
//     Direita sem inimigo
        }else if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()+1][posicoes.second.toInt()+1] == casaBranca){
            movimentacao["direita"] = posicoes.first.toInt() + 1 to posicoes.second.toInt() + 1
        }
    }else{
        //    Esquerda com oponente
        if(posicoes.first.toInt() + 1 >= 0 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()-1][posicoes.second.toInt()-1] == secondPlayer){
            if(posicoes.first.toInt() + 2 >= 0 && posicoes.second.toInt() -2 >= 0 && board[posicoes.first.toInt()-2][posicoes.second.toInt()-2] == casaBranca){
                enemyPos["esquerda"] = posicoes.first.toInt()-1 to posicoes.second.toInt() - 1
                movimentacao["esquerda"] =posicoes.first.toInt()-2 to posicoes.second.toInt() - 2
                isEnemy = true
            }
//    Esquerda sem oponente
        }else if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()-1][posicoes.second.toInt()-1] == casaBranca){
            movimentacao["esquerda"] = posicoes.first.toInt()-1 to posicoes.second.toInt()-1
        }

//    Direita com oponente
        if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() - 1 <= 7 && board[posicoes.first.toInt()-1][posicoes.second.toInt()+1] == secondPlayer){
            if(posicoes.first.toInt() - 2 >= 0 && posicoes.second.toInt() - 2 <= 7 && board[posicoes.first.toInt()-2][posicoes.second.toInt()+2] == casaBranca){
                enemyPos["direita"] = posicoes.first.toInt()-1 to posicoes.second.toInt() + 1
                movimentacao["direita"] = posicoes.first.toInt()-2 to posicoes.second.toInt()+2
                isEnemy = true
            }
//     Direita sem inimigo
        }else if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()-1][posicoes.second.toInt()+1] == casaBranca){
            movimentacao["direita"] = posicoes.first.toInt() - 1 to posicoes.second.toInt() + 1
        }
    }

    if(movimentacaoPecas(board, movimentacao, enemyPos, posicoes.first.toInt(), posicoes.second.toInt(), mainPlayer, isEnemy, casaBranca)){
        return true
    }

    return false
}

fun movimentacaoPecas(board: Array<Array<String>>, movimentacao: MutableMap<String, Pair<Int, Int>?>, enemyPosition: MutableMap<String, Pair<Int, Int>?>, linha:Int, coluna:Int, player: String, isEnemy: Boolean, casaBranca: String): Boolean{

    if(movimentacao["esquerda"] != null || movimentacao["direita"] != null) {
        val esquerda = movimentacao["esquerda"]
        val direita = movimentacao["direita"]

        val esquerdaEnemy = enemyPosition["esquerda"]
        val direitaEnemy = enemyPosition["direita"]

        println("Escolha uma movimentação:")
        // verifica se existe posição esquerda
        if(esquerda != null){
            println("==========================")
            println("Esquerda: e")
            println("Linha: ${esquerda.first}")
            println("Coluna: ${esquerda.second}")
            println("==========================")
        }
        // verifica se existe posição direita
        if(direita != null){
            println("direita: d")
            println("Linha: ${direita.first}")
            println("Coluna: ${direita.second}")
            println("==========================")
        }

        val choise = readLine().toString()
        if(isEnemy){
            when(choise.lowercase()){
                "e"-> {
                    if(esquerda != null && esquerdaEnemy != null){
                        board[linha][coluna] = casaBranca
                        board[esquerdaEnemy.first][esquerdaEnemy.second] = casaBranca
                        board[esquerda.first][esquerda.second] = player
                        return true
                    }
                }
                "d" -> {
                    if(direita != null && direitaEnemy != null){
                        board[linha][coluna] = casaBranca
                        board[direitaEnemy.first][direitaEnemy.second] = casaBranca
                        board[direita.first][direita.second] = player
                        return true
                    }
                }
            }
        }else{
            when(choise.lowercase()){
                "e"-> {
                    if(esquerda != null){
                        board[linha][coluna] = casaBranca
                        board[esquerda.first][esquerda.second] = player
                        return true
                    }
                }
                "d" -> {
                    if(direita != null){
                        board[linha][coluna] = casaBranca
                        board[direita.first][direita.second] = player
                        return true
                    }
                }
            }
        }

    }else{
        println("Não exite posições válidas de movimento para a peça Linha $linha coluna $coluna")
        return false
    }
    return false
}

fun showBoard(board: Array<Array<String>>) {
    println("   0  1  2  3  4  5  6  7")
    for(linha in 0 until 8){
        println("$linha ${board[linha].slice(0 until 8)}")
//        println("$linha ${board[linha].joinToString("    ")}")
    }
}

fun buildBoard(playerBranco: String, playerPreto: String, casaBranca: String, casaPreta: String): Array<Array<String>> {
    val tabuleiro = Array (8) { Array (8) { " " } }

    for(i in 0 until 8){
        for(j in 0 until 8){
            val isCasaPreta = (i + j) % 2 != 0
            if(isCasaPreta){
                when {
                    i < 3 -> tabuleiro[i][j] =  playerBranco
                    i > 4 -> tabuleiro[i][j] =  playerPreto
                    else -> tabuleiro[i][j] = casaPreta
                }
            }else{
                tabuleiro[i][j] = casaBranca
            }
        }
    }

    return tabuleiro
}

fun posicao(board: Array<Array<String>>, player: String): Pair<String, String>{
    val pass = false

    do{
        println("Digite uma linha: ")
        val linha = readLine().toString()
        println("Digite uma coluna: ")
        val coluna = readLine().toString()

        if(linha.contains(regex = Regex("[0-9]+")) && coluna.contains(regex = Regex("[0-9]+")) && !linha.isEmpty() && !coluna.isEmpty()){
            if(board[linha.toInt()][coluna.toInt()] == player){
                val result = linha to coluna
                return result
            }else{
                println("Não existe peças válidas nesta posição do tabuleiro!")
            }
        }
    }while (!pass)
    return "" to ""
}