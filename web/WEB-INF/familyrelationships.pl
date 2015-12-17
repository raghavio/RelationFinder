male(james).
male(john).
male(robert).
male(michael).
male(william).
male(raghav).
male(david).
male(charles).

female(mary).
female(patrica).
female(linda).
female(barbara).
female(elizabeth).
female(maria).
female(susan).
female(katrina).

spouse(james, mary).
spouse(mary, james).
spouse(michael, elizabeth).
spouse(elizabeth, michael).
spouse(william, patrica).
spouse(patrica, william).
spouse(raghav, katrina).
spouse(katrina, raghav).
spouse(susan, david).
spouse(david, susan).

spouse(john, barbara).
spouse(barbara, john).

parent(james, mary, michael).
parent(james, mary, william).
parent(james, mary, linda).

parent(john, barbara, elizabeth).
parent(john, barbara, maria).
parent(john, barbara, robert).

parent(michael, elizabeth, raghav).
parent(michael, elizabeth, susan).
parent(raghav, katrina, charles).

husband(Husband, Woman) :- male(Husband), spouse(Husband, Woman).
wife(Wife, Man) :- female(Wife), spouse(Wife, Man).

father(Father, Child) :- male(Father), parent(Father, _, Child).
mother(Mother, Child) :- female(Mother), parent(_, Mother, Child).

son(Son, Parent) :-
    male(Parent), son(Son, Parent, _);
    female(Parent), son(Son, _, Parent).
son(Son, Father, Mother) :- male(Son), parent(Father, Mother, Son).
daughter(Daughter, Parent) :-
    male(Parent), daughter(Daughter, Parent, _);
    female(Parent), daughter(Daughter, _, Parent).
daughter(Daughter, Father, Mother) :- female(Daughter), parent(Father, Mother, Daughter).

sister(Sister, Y) :-
    female(Sister), parent(Father, Mother, Sister), parent(Father, Mother, Y), Sister \= Y.
brother(Brother, Y) :-
    male(Brother), parent(Father, Mother, Brother), parent(Father, Mother, Y), Brother \= Y.

father_in_law(FIL, Y) :- male(FIL), spouse(Spouse, Y), parent(FIL, _, Spouse).
mother_in_law(MIL, Y) :- female(MIL), spouse(Spouse, Y), parent(_, MIL, Spouse).

son_in_law(SIL, PIL) :-
    male(PIL), son_in_law(SIL, PIL, _);
    female(PIL), son_in_law(SIL, _, PIL).
son_in_law(SIL, Father, Mother) :- male(SIL), spouse(Daughter, SIL), parent(Father, Mother, Daughter).
daughter_in_law(DIL, PIL) :-
    male(PIL), daughter_in_law(DIL, PIL, _);
    female(PIL), daughter_in_law(DIL, _, PIL).
daughter_in_law(DIL, Father, Mother) :- female(DIL), spouse(Son, DIL), parent(Father, Mother, Son).

paternal_grandson(Grandson, Grandparent) :-
    male(Grandparent), paternal_grandson(Grandson, Grandparent, _);
    female(Grandparent), paternal_grandson(Grandson, _, Grandparent).
paternal_grandson(Grandson, Grandfather, Grandmother) :-
    parent(Grandfather, Grandmother, Son), son(Grandson, Son, _).
paternal_granddaughter(Granddaughter, Grandparent) :-
    male(Grandparent), paternal_granddaughter(Granddaughter, Grandparent, _);
    female(Grandparent), paternal_granddaughter(Granddaughter, _, Grandparent).
paternal_granddaughter(Granddaughter, Grandfather, Grandmother) :-
    parent(Grandfather, Grandmother, Son), daughter(Granddaughter, Son, _).

paternal_grandfather(Grandfather, Y) :-
    father(Father, Y), father(Grandfather, Father).
paternal_grandmother(Grandmother, Y) :-
    father(Father, Y), mother(Grandmother, Father).

maternal_grandfather(Grandfather, Y) :-
    mother(Mother, Y), father(Grandfather, Mother).
maternal_grandmother(Grandmother, Y) :-
    mother(Mother, Y), mother(Grandmother, Mother).

maternal_grandson(Grandson, Grandparent) :-
    male(Grandparent), maternal_grandson(Grandson, Grandparent, _);
    female(Grandparent), maternal_grandson(Grandson, _, Grandparent).
maternal_grandson(Grandson, Grandfather, Grandmother) :-
    parent(Grandfather, Grandmother, Daughter), mother(Daughter, Grandson), male(Grandson).
maternal_granddaughter(Granddaughter, Grandparent) :-
    male(Grandparent), maternal_granddaughter(Granddaughter, Grandparent, _);
    female(Grandparent), maternal_granddaughter(Granddaughter, _, Grandparent).
maternal_granddaughter(Granddaughter, Grandfather, Grandmother) :-
    parent(Grandfather, Grandmother, Daughter), mother(Daughter, Granddaughter), female(Granddaughter).

% Bhabhi = Brother's wife
bhabhi(Bhabhi, Y) :- brother(Brother, Y), female(Bhabhi), spouse(Bhabhi, Brother).

% Jija = Sister's husband
jija(JijaJi, Y) :- sister(Sister, Y), husband(JijaJi, Sister).

sister_in_law(SIL, Y) :- spouse(X, Y), sister(SIL, X).
brother_in_law(BIL, Y) :- spouse(X, Y), brother(BIL, X).

% Uncle = Father's brother
% Aunty = Uncle's wife
uncle(Uncle, Y) :- father(Father, Y), brother(Uncle, Father).
aunty(Aunty, Y) :- uncle(Uncle, Y), wife(Aunty, Uncle).

% Bua = Father's sister
% Phupha = linda's husband
bua(Bua, Y) :- father(Father, Y), sister(Bua, Father).
phupha(Phupha, Y) :- bua(Bua, Y), husband(Phupha, Bua).

% Mama = Mother's brother
% Mami = Mama's wife
mama(Mama, Y) :- mother(Mother, Y), brother(Mama, Mother).
mami(Mami, Y) :- mama(Mama, Y), wife(Mami, Mama).

% Mausii = Mother's sister
% Mausaa = Mausii's Husband
mausii(Mausii, Y) :- mother(Mother, Y), sister(Mausii, Mother).
mausaa(Mausaa, Y) :- mausii(Mausii, Y), husband(Mausaa, Mausii).

% Bhatijaa, Bhanja = Nephew
% Bhatiiji, Bhanji = Niece
bhatijaa(Nephew, Y) :- brother(Brother, Y), son(Nephew, Brother, _).
bhatiiji(Bhatiiji, Y) :- brother(Brother, Y), daughter(Bhatiiji, Brother, _).
bhanja(Bhanja, Y) :- sister(Sister, Y), son(Bhanja, _, Sister).
bhanji(Bhanji, Y) :- sister(Sister, Y), daughter(Bhanji, _, Sister).

% jitotee(Jitotee, Y) :- husband(Husband, Y),
query_family(P1, P2, P) :-
    member(P, [husband, wife, father, mother, son, daughter, sister, brother,
                father_in_law, mother_in_law, son_in_law, daughter_in_law,
                paternal_grandson, paternal_granddaughter, paternal_grandfather,
                paternal_grandmother, maternal_grandfather, maternal_grandmother,
                maternal_grandson, maternal_granddaughter, bhabhi, jija,
                sister_in_law, brother_in_law, uncle, aunty, bua, phupha, mama,
                mami, mausii, mausaa, bhatijaa, bhatiiji, bhanja, bhanji]),
    call(P, P1, P2).
