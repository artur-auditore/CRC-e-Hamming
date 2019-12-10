#ou exclusivo (para a divisão)
def xor(a, b):
    result = []

    #percorre todos os bits, se cada posição for igual, é zero, se não é 1
    for i in range(1, len(b)):
        if a[i] == b[i]:
            result.append('0')
        else:
            result.append('1')
    
    return ''.join(result) #retorna como uma string

#calculo do grau 
def calcula_grau(gx):
    ct = 0
    grau = 0
    for i in range(len(gx)):
        if gx[i] == '0' and ct == 0:
            pass
        else:
            ct += 1
            grau += 1
    return grau - 1

#validação do gx
def verifica(gx):
    if (len(gx) >= 2 and gx[0] == '1' and gx[len(gx)-1] == '1'):
        return True
    else:   
        return False

#forma a palavra para o que envia
def palavra_seeder(msg, grau):
    zeros = '0' * grau
    return msg + zeros

#forma a palavra para o que recebe
def palavra_rec(msg, crc):
    return msg + crc

#faz a divisão
def divisao(dividendo, divisor):
    pick = len(divisor) #pega o tamanho do divisor
    temp = dividendo[0 : pick] #pega o primeiro 'pedaço' do dividendo do mesmo tamanho do divisor

    #enquanto 'pick' for menor que o tamanho do dividendo mais um bit vai sendo adicionado a 'temp'
    #que é o resto da divisão
    while pick < len(dividendo): 
        if temp[0] == '1':  
            temp = xor(divisor, temp) + dividendo[pick] 
        else: 
            temp = xor('0'*pick, temp) + dividendo[pick] 
        pick += 1 #incrementa pra pegar a próxima posição do dividendo

    if temp[0] == '1': 
        temp = xor(divisor, temp) 
    else: 
        temp = xor('0'*pick, temp) 
   
    checkword = temp 
    return checkword

def main():

    print("------ Sender side -----\n")
    word = input("Palavra a ser transmitida: ")
    
    while True:
        gx = input("Polinômio gerador: ")
        if(verifica(gx)):
            break
        else:
            print("Polinômio inválido.")

    grau = calcula_grau(gx)
    print("O grau do polinômio é ", grau)
    word_encoded = palavra_seeder(word, grau)
    print("Palavra a ser enviada: ", word_encoded)
    crc = divisao(word_encoded, gx)
    print("CRC:", crc)

    print("------ Receiver side ------\n")
    rec_word = palavra_rec(word, crc)
    print("Palavra recebida: ", rec_word)
    resto = divisao(rec_word, crc)
    print("Resto da divisão: ", resto)

    if resto == '0'*(grau-1):
        print("A palavra chegou corretamente.\n")
    else:
        print("A palavra não chegou corretamente.\n")

if __name__ == "__main__":
    main()