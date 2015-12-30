==========================================================================================================
||          MI-RUN  - JVM pro Javu v Jav�                                                               ||
==========================================================================================================


Vypracoval: Jan Effenberger (effenjan)


Program pro interpretaci Javovosk�ho bytecode v Jav�.
Program m� sv� omezen� pro vstupn� bytecode, kter� jsou pops�na v p��slu�n� ��sti n�e.

P��klad, kter� je p�ilo�en, �e�� splnitelnost SAT formule, kde vstupem pouze znaky a-z, !, &, |.
Tato formule mus� b�t zad�na v postfixov�m form�tu.

Sou��st� josu t�i vstupn� soubory a jeden v�stupn�.



----------------------------------------------------------------------------------------------------------
== Vstupn� soubor pro SAT solver

Mus� obsahovat pr�v� dva ��dky, kde prvn� je vstupem
do SAT solveru. Druh� ��dek je vyhrazen pro cestu k
bytecode souboru, kter�mu je p�ed�n vstup.


== V�stupn� soubor pro SAT solver

V�sutpn� soubor je pouze jeden a v�stup z SAT solveru
je v�dy p�ipojen na konec souboru s uveden�m �asem.


== P��klady

Jsou p�ilo�eny t�i soubory input[1-3], kter� jsou vstupn�mi soubory pro formule SAT a �e�� dan� formule.


----------------------------------------------------------------------------------------------------------

== Spu�t�n� jin�ho bytecode ne� SAT solveru

JVM dok�e zpracovat Javovsk� classfile, tud� je mo�n�
spou�t�t jin� classfile ne� p�ilo�en� p��klad. Ov�em
takov� class file mus� spl�ovat n�sleduj�c� omezen�:


== Omezen� pro vstupn� bytecode

Bytecode, kter� je vstupem pro JVM mus� obsahovat
pr�v� jednu metodu main s n�sleduj�c� signaturou:
public static String main(String) throws Exception 

===== Pou�it� instrukce

JVM dok�e zpracovat n�sleduj�c� instrukce:
aconst_null
    aload
    aload_0
    aload_1
    aload_2
    aload_3
    areturn
    arraylength
    astore
    astore_1
    astore_2
    astore_3
    baload
    bastore
    bipush
    dup
    getstatic
    goto
    checkcast
    i2c
    iadd
    iand
    iconst_0
    iconst_1
    iconst_2
    iconst_3
    if_icmpeq
    if_icmpge
    if_icmplt
    if_icmpne
    ifeq
    ifle
    ifge
    iflt
    ifne
    ifnull
    iinc
    iload
    iload_1
    iload_2
    iload_3
    imul
    invokespecial
    invokestatic
    invokevirtual
    ireturn
    ishl
    istore
    istore_2
    istore_3
    isub
    ldc
    new
    newarray
    pop
    putfield
    getfield
    nop
    return












































