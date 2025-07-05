package org.example


fun main() {
    var win = false
    var turn = true
    val playerBranco = "⚫"
    val playerPreto = "⚪"
    val casaBranca = " "
    val casaPreta = " "
    val board: Array<Array<String>> = buildBoard(playerBranco, playerPreto, casaBranca, casaPreta)

    while (!win){
        showBoard(board)
        if(turn){
            println("$playerBranco Turn")
            movimentoBranca(board, playerBranco, playerPreto, casaPreta)
            turn = false
        }else{
            println("$playerPreto Turn")
            movimentoPreta(board, playerPreto, playerBranco, casaPreta)
            turn = true
        }
    }
}
fun movimentoPreta(board: Array<Array<String>>, playerPreto: String, playerBranco: String, casaBranca: String){
    var choiseBoo = false
    var isEnemy = false
    val movimentacao: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )
    val enemyPos: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )
    val posicoes = posicao()
    
    while (!choiseBoo){
        //Verifica as duas movimentações possiveis
        if(board[posicoes.first.toInt()][posicoes.second.toInt()] == playerPreto){
            //Esquerda com oponente
            if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()-1][posicoes.second.toInt()-1] == playerBranco){
                if(posicoes.first.toInt() - 2 >= 0 && posicoes.second.toInt() -2 >= 0 && board[posicoes.first.toInt()-2][posicoes.second.toInt()-2] == casaBranca){
                    enemyPos["esquerda"] = posicoes.first.toInt()-1 to posicoes.second.toInt() - 1
                    movimentacao["esquerda"] =posicoes.first.toInt()-2 to posicoes.second.toInt() - 2
                    isEnemy = true
                }
//          Esquerda sem oponente
            }else if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()-1][posicoes.second.toInt()-1] == casaBranca){
                movimentacao["esquerda"] = posicoes.first.toInt()-1 to posicoes.second.toInt()-1
            }

            //Direita com oponente
            if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()-1][posicoes.second.toInt()+1] == playerBranco){
                if(posicoes.first.toInt() - 2 >= 0 && posicoes.second.toInt() + 2 <= 7 && board[posicoes.first.toInt()-2][posicoes.second.toInt()+2] == casaBranca){
                    enemyPos["direita"] = posicoes.first.toInt()-1 to posicoes.second.toInt() + 1
                    movimentacao["direita"] = posicoes.first.toInt()-2 to posicoes.second.toInt()+2
                    isEnemy = true
                }
//          Direita sem inimigo
            }else if(posicoes.first.toInt() - 1 >= 0 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()-1][posicoes.second.toInt()+1] == casaBranca){
                movimentacao["direita"] = posicoes.first.toInt() - 1 to posicoes.second.toInt() + 1
            }

            if(verificaMovimentacao(board, movimentacao, enemyPos, posicoes.first.toInt(), posicoes.second.toInt(), playerPreto, isEnemy, casaBranca)){
                choiseBoo = true
            }

        }else{
            println("Não existe uma peça válida nesta posição. Tá Loco?")
        }
    }
}

fun movimentoBranca(board: Array<Array<String>>, playerBranco: String, playerPreto: String, casaBranca: String){
    var choiseBoo = false
    var isEnemy = false
    val movimentacao: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )
    val positionEnemy: MutableMap<String, Pair<Int, Int>?> = mutableMapOf(
        "esquerda" to null,
        "direita" to null
    )

    while (!choiseBoo){
        val posicoes = posicao()

        //Verifica as duas movimentações possiveis
        //Esquerda
        if(board[posicoes.first.toInt()][posicoes.second.toInt()] == playerBranco){
            if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()+1][posicoes.second.toInt()-1] == playerPreto){
                if(posicoes.first.toInt() + 2 <= 7 && posicoes.second.toInt() - 2 >= 0 && board[posicoes.first.toInt()+2][posicoes.second.toInt()-2] == casaBranca){
                    positionEnemy["esquerda"] = posicoes.first.toInt()+1 to posicoes.second.toInt() - 1
                    movimentacao["esquerda"] = posicoes.first.toInt()+2 to posicoes.second.toInt() - 2
                    isEnemy = true
                }

            }else if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() - 1 >= 0 && board[posicoes.first.toInt()+1][posicoes.second.toInt()-1] == casaBranca){
                movimentacao["esquerda"] = posicoes.first.toInt()+1 to posicoes.second.toInt()-1
            }
            //Direita
            if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()+1][posicoes.second.toInt()+1] == playerPreto){
                if(posicoes.first.toInt() + 2 <= 7 && posicoes.second.toInt() + 2 <= 7 && board[posicoes.first.toInt()+2][posicoes.second.toInt()+2] == casaBranca){
                    positionEnemy["direita"] = posicoes.first.toInt()+1 to posicoes.second.toInt() + 1
                    movimentacao["direita"] = posicoes.first.toInt()+2 to posicoes.second.toInt() + 2
                    isEnemy = true
                }
            }else if(posicoes.first.toInt() + 1 <= 7 && posicoes.second.toInt() + 1 <= 7 && board[posicoes.first.toInt()+1][posicoes.second.toInt()+1] == casaBranca){
                movimentacao["direita"] = posicoes.first.toInt() + 1 to posicoes.second.toInt() + 1
            }

            if(verificaMovimentacao(board, movimentacao,positionEnemy, posicoes.first.toInt(), posicoes.second.toInt(), playerBranco, isEnemy, casaBranca)){
                choiseBoo = true
            }

        }else{
            println("Não existe uma peça válida nesta posição. Tá Loco?")
        }
    }
}

fun verificaMovimentacao(board: Array<Array<String>>, movimentacao: MutableMap<String, Pair<Int, Int>?>, enemyPosition: MutableMap<String, Pair<Int, Int>?>, linha:Int, coluna:Int, player: String, isEnemy: Boolean, casaBranca: String): Boolean{

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

    //Posicionar peças brancas
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
//            if((i == 0 || i == 2) && j % 2 != 0){
//                tabuleiro[i][j] = "B"
//            }else if(i == 1 && j % 2 == 0){
//                tabuleiro[i][j] = "B"
//            }
        }
    }

//    // Posicionar peças pretas
//    for(i in 7 downTo 5){
//        for(j in 0..7){
//            if((i == 7 || i == 5) && j % 2 == 0){
//                tabuleiro[i][j] = "P"
//            }else if(i == 6 && j % 2 != 0){
//                tabuleiro[i][j] = "P"
//            }
//        }
//    }

    return tabuleiro
}

fun posicao(): Pair<String, String>{
    val pass = false

    do{
        println("Digite uma linha: ")
        val linha = readLine().toString()
        println("Digite uma coluna: ")
        val coluna = readLine().toString()

        if(linha.contains(regex = Regex("[0-9]+")) && coluna.contains(regex = Regex("[0-9]+")) && !linha.isEmpty() && !coluna.isEmpty()){
            val result = linha to coluna
            return result
        }
    }while (!pass)
    return "" to ""
}