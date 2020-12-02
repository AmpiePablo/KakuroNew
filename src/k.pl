

spacegen(N, M) :-
    spacegen_tophalf(N, M),
    spacegen_reflect(N, M),
    spacegen_middle(N, M),
    spacegen_midreflect(N, M),
    fixlonghorizontal(N, M),
    fixlongvertical(N, M),
    fixlongmidhorizontal(N, M),
    fixlongmidvertical(N, M).


spacegen_tophalf(N, M) :-
    Nhf is N div 2,
    Mm1 is M-1,
    spacegen_tophalf(1, 1, Nhf, Mm1).

spacegen_tophalf(I, J, Nhf, Mm1) :-
    I =:= Nhf;
    (   J =:= Mm1
    ->  I1 is I+1,
        spacegen_tophalf(I1, 1, Nhf, Mm1)
    );
    random(R), I1 is I+1, J1 is J+1,
    (   R < 0.3
    ->  makeblank(I, J),
        makeblank(I1, J),
        makeblank(I, J1),
        makeblank(I1, J1)
    ;   true
    ),
    spacegen_tophalf(I, J1, Nhf, Mm1).


spacegen_reflect(N, M) :-
    Nhf1 is N div 2 + 1,
    spacegen_reflect(1, 1, N, Nhf1, M).

spacegen_reflect(I, J, N, Nhf1, M) :-
    I =:= Nhf1;
    (   J =:= M
    ->  I1 is I+1,
        spacegen_reflect(I1, 1, N, Nhf1, M)
    );
    (   blank(I,J)
    ->  Ni is N-I,
        Mj is M-J,
        makeblank(Ni, Mj)
    ;   true
    ),
    J1 is J+1,
    spacegen_reflect(I, J1, N, Nhf1, M).


spacegen_middle(N, M) :-
    odd(N) ->
    (   I is N div 2,
        I1 is I+1,
        Mhf1 is M div 2 + 1,
        spacegen_middle(I, I1, 1, Mhf1)
    );
    true.
spacegen_middle(I, I1, J, Mhf1) :-
    J =:= Mhf1;
    random(R), J1 is J+1,
    (   R < 0.3
    ->  makeblank(I, J),
        makeblank(I1, J),
        makeblank(I, J1),
        makeblank(I1, J1)
    ;   true
    ),
    spacegen_middle(I, I1, J1, Mhf1).


odd(X) :- X /\ 1 =:= 1.


spacegen_midreflect(N, M) :-
    I is N div 2,
    Mhf1 is M div 2 + 1,
    spacegen_midreflect(I, 1, N, M, Mhf1).

spacegen_midreflect(I, J, N, M, Mhf1) :-
    J =:= Mhf1;
    Ni is N-I, Mj is M-J,
    (   blank(Ni, J)
    ->  makeblank(I, Mj)
    ;   true
    ),
    (   blank(I, J)
    ->  makeblank(Ni, Mj)
    ;   true
    ),
    J1 is J+1,
    spacegen_midreflect(I, J1, N, M, Mhf1).


fixlonghorizontal(N, M) :-
    Nhf is N div 2,
    fixlonghorizontal(2, 1, M, N, M, Nhf).

fixlonghorizontal(I, J, Gs, N, M, Nhf) :-
    I =:= Nhf;
    (   J =:= M
    ->  I1 is I+1,
        fixlonghorizontal(I1, 1, M, N, M, Nhf)
    );
    (   blank(I, J)
    ->  (   Gs =:= M
        ->  Ags is J
        ;   Ags is Gs
        )
    ;   Ags is M
    ),
    J1 is J+1,
    (   J-Ags+1 > 9
    ->  Low is Ags+2, High is J-2,
        random(Low, High, RC),
        makesolid(I, RC),
        check1ss(I, RC),
        On is N-I, Om is M-RC,
        makesolid(On, Om),
        check1ss(On, Om),
        fixlonghorizontal(I, J1, RC+1, N, M, Nhf)
    ;   fixlonghorizontal(I, J1, Ags, N, M, Nhf)
    ).



fixlongvertical(N, M) :-
    Mhf is M div 2,
    fixlongvertical(2, 1, N, N, M, Mhf).

fixlongvertical(I, J, Gs, N, M, Mhf) :-
    J =:= Mhf;
    (   I =:= M
    ->  J1 is J+1,
        fixlongvertical(1, J1, N, N, M, Mhf)
    );
    (   blank(I, J)
    ->  (   Gs =:= N
        ->  Ags is I
        ;   Ags is Gs
        )
    ;   Ags is N
    ),
    I1 is I+1,
    (   I-Ags+1 > 9
    ->  Low is Ags+2, High is I-2,
        random(Low, High, RR),
        makesolid(RR, J),
        check1ss(RR, J),
        On is N-RR, Om is M-J,
        makesolid(On, Om),
        check1ss(On, Om),
        fixlongvertical(I1, J, RR+1, N, M, Mhf)
    ;   fixlongvertical(I1, J, Ags, N, M, Mhf)
    ).



fixlongmidhorizontal(N, M) :-
    Nhf is N div 2,
    NhfC1 is N - Nhf + 1,
    Mhf1 is M div 2 + 1,
    fixlongmidhorizontal(Nhf, 1, M, N, M, NhfC1, Mhf1).

fixlongmidhorizontal(I, J, Gs, N, M, NhfC1, Mhf1) :-
    I =:= NhfC1;
    (   J =:= Mhf1
    ->  I1 is I+1,
        fixlongmidhorizontal(I1, 1, M, N, M, NhfC1, Mhf1)
    );
    (   blank(I, J)
    ->  (   Gs =:= M
        ->  Ags is J
        ;   Ags is Gs
        )
    ;   Ags is M
    ),
    J1 is J+1,
    (   J-Ags+1 >= 5
    ->  RC is Ags+2,
        makesolid(I, RC),
        check1ss(I, RC),
        On is N-I, Om is M-RC,
        makesolid(On, Om),
        check1ss(On, Om),
        fixlongmidhorizontal(I, J1, RC+1, N, M, NhfC1, Mhf1)
    ;   fixlongmidhorizontal(I, J1, Ags, N, M, NhfC1, Mhf1)
    ).



fixlongmidvertical(N, M) :-
    Mhf is M div 2,
    MhfC1 is M - Mhf + 1,
    Nhf1 is N div 2 + 1,
    fixlongmidvertical(1, Mhf, N, N, M, MhfC1, Nhf1).

fixlongmidvertical(I, J, Gs, N, M, MhfC1, Nhf1) :-
    J =:= MhfC1;
    (   I =:= Nhf1
    ->  J1 is J+1,
        fixlongmidvertical(1, J1, N, N, M, MhfC1, Nhf1)
    );
    (   blank(I, J)
    ->  (   Gs =:= N
        ->  Ags is I
        ;   Ags is Gs
        )
    ;   Ags is N
    ),
    I1 is I+1,
    (   I-Ags+1 >= 5
    ->  RR is Ags+2,
        makesolid(RR, J),
        check1ss(RR, J),
        On is N-RR, Om is M-J,
        makesolid(On, Om),
        check1ss(On, Om),
        fixlongmidvertical(I1, J, RR+1, N, M, MhfC1, Nhf1)
    ;   fixlongmidvertical(I1, J, Ags, N, M, MhfC1, Nhf1)
    ).


makeblank(I, J) :- \+ blank(I, J) -> assert(blank(I, J)); true.

makesolid(I, J) :- blank(I, J) -> retract(blank(I, J)); true.


check1ss(I, J) :-
    Im1 is I-1, Im2 is I-2, Ip1 is I+1, Ip2 is I+2,
    Jm1 is J-1, Jm2 is J-2, Jp1 is J+1, Jp2 is J+2,

    (   (   blank(Im1, J), \+ blank(Im2, J) )
    ->  (   makesolid(Im1, J), check1ss(Im1, J) )
    ;   true
    ),

    (   (   blank(Ip1, J), \+ blank(Ip2, J) )
    ->  (   makesolid(Ip1, J), check1ss(Ip1, J) )
    ;   true
    ),

    (   (   blank(I, Jm1), \+ blank(I, Jm2))
    ->  (   makesolid(I, Jm1), check1ss(I, Jm2) )
    ;   true
    ),

    (   (   blank(I, Jp1), \+ blank(I, Jp2))
    ->  (   makesolid(I, Jp1), check1ss(I, Jp2) )
    ;   true
    ).


printspaces(N, M) :-
    printspaces(0, 0, N, M).

printspaces(I, J, N, M) :-
    I =:= N;
    J =:= M ->
    (   nl, I1 is I+1,
        printspaces(I1, 0, N, M)
    );
    (
        (   blank(I, J)
        ->  write('   ')
        ;   write(' B ')
        ),
        J1 is J+1,
        printspaces(I, J1, N, M)
    ).


reset(N, M) :-
    reset(0, 0, N, M).

reset(I, J, N, M) :-
    I =:= N;
    (   J =:= M
    ->  I1 is I+1,
        reset(I1, 0, N, M)
    );
    (   makesolid(I, J),
        (   desasignar(I, J); true),
        J1 is J+1,
        reset(I, J1, N, M)
    ).


/****************************************************
 *                 definir solución
 ****************************************************/



sum(L, R) :-
    (   [H|T] = L
    ->  sum(T, S),
        R is H+S
    ;   R is 0
    ).


shuffle(L, R) :-
    length(L, Len),
    Len1 is Len + 1,
    shuffle(L, Len1, R).

shuffle(L, Len, R) :-
    (   Len > 2
    ->  random(1, Len, Chosen),
        extract(L, Chosen, RL, RE),
        NLen is Len - 1,
        shuffle(RL, NLen, S),
        R = [RE|S]
    ;   R = L
    ).

extract(L, I, RL, RE) :-
    (   [H|T] = L
    ->  (   I =:= 1
        ->  RE is H,
            RL = T
        ;   Im1 is I - 1,
            extract(T, Im1, RLR, RE),
            RL = [H | RLR]
        )
    ;   RL = []
    ).





getcolumn(I, J, RC) :-
    (   \+ blank(I, J)
    ->  RC = [I, J]
    ;   Im1 is I - 1,
        getcolumn(Im1, J, RC)
    ).




getrow(I, J, RR) :-
    (   \+ blank(I, J)
    ->  RR = [I, J]
    ;   Jm1 is J - 1,
        getrow(I, Jm1, RR)
    ).




/*
 ********************************************
 *             EL  PLAN   B
 ********************************************
 */

asignar(I, J, N) :-
    retract(num(I, J, _)),
    assert(num(I, J, N));
    assert(num(I, J, N)).

desasignar(I, J) :-
    retract(num(I, J, _)).

range(I, F, R) :-
    (   I > F
    ->  R = []
    ;   I1 is I+1,
        range(I1, F, S),
        R = [I | S]
    ).

llenar(N, M) :-
    llenar(1, 1, N, M).

llenar(I, J, N, M) :-
    I =:= N;
    (   J =:= M
    ->  I1 is I+1,
        llenar(I1, 1, N, M)
    ;   (   blank(I, J)
        ->  getcolumn(I, J, Col),
            numsincolumn(Col, ColList),
            getrow(I, J, Row),
            numsinrow(Row, RowList),
            range(1, 9, All),
            subtract(All, ColList, S),
            subtract(S, RowList, Options),
            shuffle(Options, SOptions),
            (   trynextoption(I, J, N, M, SOptions)
            ;   makesolid(I, J),
                check1ss(I, J),
                NmI is N - I, MmJ is M - J,
                makesolid(NmI, MmJ),
                check1ss(NmI, MmJ),
                J1 is J + 1,
                llenar(I, J1, N, M)
            )
        ;   J1 is J + 1,
            llenar(I, J1, N, M)
        )
    ).

trynextoption(I, J, N, M, Options) :-
    (   [OH | OT] = Options
    ->  asignar(I, J, OH),
        J1 is J + 1,
        (   llenar(I, J1, N, M)
        ;   trynextoption(I, J, N, M, OT)
        )
    ;   desasignar(I, J),
        false
    ).


numsincolumn([I, J], R) :-
    I1 is I + 1,
    numsincolumn(I1, J, R).

numsincolumn(I, J, R) :-
    (   \+ blank(I, J)
    ->  R = []
    ;   I1 is I + 1,
        (   num(I, J, N)
        ->  numsincolumn(I1, J, S),
            R = [N | S]
        ;   numsincolumn(I1, J, R)
        )
    ).

columnsum([I, J], R) :-
    numsincolumn([I, J], S),
    sum(S, R).

numsinrow([I, J], R) :-
    J1 is J + 1,
    numsinrow(I, J1, R).

numsinrow(I, J, R) :-
    (   \+ blank(I, J)
    ->  R = []
    ;   J1 is J + 1,
        (   num(I, J, N)
        ->  numsinrow(I, J1, S),
            R = [N | S]
        ;   numsinrow(I, J1, R)
        )
    ).

rowsum([I, J], R) :-
    numsinrow([I, J], S),
    sum(S, R).


printkakuro(N, M) :-
    printkakuro(0, 0, N, M).

printkakuro(I, J, N, M) :-
    I =:= N;
    J =:= M ->
    (   nl, I1 is I+1,
        printkakuro(I1, 0, N, M)
    );
    (
        (   blank(I, J)
        ->  (   num(I, J, Num)
            ->  write(' '),
                write(Num),
                write(' ')
            ;   write(' _ ')
            )
        ;   write('   ')
        ),
        J1 is J+1,
        printkakuro(I, J1, N, M)
    ).



getkakuro(N, M, R) :-
    getkakuro(0, 0, N, M, R).

getkakuro(I, J, N, M, R) :-
    (   I =:= N
    ->  R = []
    ;   (   J =:= M
        ->  I1 is I + 1,
            getkakuro(I1, 0, N, M, R)
        ;   J1 is J + 1,
            getkakuro(I, J1, N, M, S),
            (   blank(I, J)
            ->  num(I, J, Num),
                R = [Num | S]
            ;   columnsum([I, J], ColSum),
                rowsum([I, J], RowSum),
                (   (ColSum > 0; RowSum > 0)
                ->  number_string(ColSum, CString),
                    number_string(RowSum, RString),
                    string_concat(CString, "-", String1),
                    string_concat(String1, RString, String2),
                    R = [String2 | S]
                ;   R = [n | S]
                )
            )
        )
    ).



generarkakuro(N, M, R) :-
    (   retract(blank(-1,-1)),
        assert(blank(-1,-1))
    ;   assert(blank(-1,-1))
    ),
    (   retract(num(-1,-1,-1)),
        assert(num(-1,-1,-1))
    ;   assert(num(-1,-1,-1))
    ),
    reset(N, M),
    spacegen(N, M),
    llenar(N, M),
    getkakuro(N, M, R).














