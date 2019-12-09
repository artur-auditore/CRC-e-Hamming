#ou exclusivo (para a divisão)
def xor(a, b):
    result = []

    for i in range(1, len(b)):
        if a[i] == b[i]:
            result.append('0')
        else:
            result.append('1')
    
    return ''.join(result)

#calculo do grau 
def calcula_grau(gx):
    ct = 0
    grau = 0
    for i in range(len(gx)):
        if gx[i] == '0' and ct == '0':
            pass
        else:
            ct += 1
            grau += 1
    return grau - 1

#verificação do gx (falta fazer)
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
    pick = len(divisor) 
    temp = dividendo[0 : pick] 
   
    while pick < len(dividendo): 
   
        if temp[0] == '1':  
            temp = xor(divisor, temp) + dividendo[pick] 
        else: 
            temp = xor('0'*pick, temp) + dividendo[pick] 
        pick += 1

    if temp[0] == '1': 
        temp = xor(divisor, temp) 
    else: 
        temp = xor('0'*pick, temp) 
   
    checkword = temp 
    return checkword 

def main():

    print("------ Sender side -----\n")
    word = input("Palavra a ser transmitida: ")
    gx = input("Polinômio gerador: ")
    grau = calcula_grau(gx)
    print("o grau do polinômio é ", grau)
    word_encoded = palavra_seeder(word, grau)
    print("palavra a ser enviada: ", word_encoded)
    crc = divisao(word_encoded, gx)
    print("crc:", crc)

    print("------ Receiver side ------\n")
    rec_word = palavra_rec(word, crc)
    print("palavra recebida: ", rec_word)
    resto = divisao(rec_word, crc)
    print("resto da divisão: ", resto)
    if resto == '0'*(grau-1):
        print("A palavra chegou corretamente.\n")
    else:
        print("A palavra não chegou corretamente.\n")

if __name__ == "__main__":
    main()