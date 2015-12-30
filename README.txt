==========================================================================================================
||          MI-RUN  - JVM pro Javu v Javì                                                               ||
==========================================================================================================


Vypracoval: Jan Effenberger (effenjan)


Program pro interpretaci Javovoského bytecode v Javì.
Program má svá omezení pro vstupní bytecode, která jsou popsána v pøíslušné èásti níže.

Pøíklad, který je pøiložen, øeší splnitelnost SAT formule, kde vstupem pouze znaky a-z, !, &, |.
Tato formule musí být zadána v postfixovém formátu.

Souèástí josu tøi vstupní soubory a jeden výstupní.



----------------------------------------------------------------------------------------------------------
== Vstupní soubor pro SAT solver

Musí obsahovat právì dva øádky, kde první je vstupem
do SAT solveru. Druhý øádek je vyhrazen pro cestu k
bytecode souboru, kterému je pøedán vstup.


== Výstupní soubor pro SAT solver

Výsutpní soubor je pouze jeden a výstup z SAT solveru
je vždy pøipojen na konec souboru s uvedeným èasem.


== Pøíklady

Jsou pøiloženy tøi soubory input[1-3], které jsou vstupními soubory pro formule SAT a øeší dané formule.


----------------------------------------------------------------------------------------------------------

== Spuštìní jiného bytecode než SAT solveru

JVM dokáže zpracovat Javovský classfile, tudíž je možné
spouštìt jiné classfile než pøiložený pøíklad. Ovšem
takový class file musí splòovat následující omezení:


== Omezení pro vstupní bytecode

Bytecode, který je vstupem pro JVM musí obsahovat
právì jednu metodu main s následující signaturou:
public static String main(String) throws Exception 

===== Použité instrukce

JVM dokáže zpracovat následující instrukce:
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












































