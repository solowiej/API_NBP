m      Zadanie NBP API:
     
      Stwórz main'a w którym pytasz użytkownika o 4 parametry, są nimi:
      - kod waluty
      - data początku zakresu
      - data końca zakresu  (zweryfikuj że data końca jest
           późniejsza niż początku zakresu)
      - rodzaj tabeli
           - jeśli użytkownik wybierze ASK/BID, chodzi o tabelę C
           - jeśli użytkownik wybierze MID, chodzi o tabelę A/B
           (możemy przyjąć że będzie to zawsze tabela A, przy wybraniu
            drugiej opcji).
     
     
      Jako wynik aplikacji wypisz System.out.println() zapytanie które
      należy wywołać na API by otrzymać wynik zgodny z danymi które
      wprowadził użytkownik.
     
      Przetestuj działanie aplikacji - sprawdź czy zapytanie (skopiuj je do
      przeglądarki) zwraca poprawne wyniki.

1. Zastanów się jak można zmienić budowę aplikacji, aby była bardziej obiektowa (uniknąć metod statycznych i przenieść część odpowiedzialności na klasy).

2. Dodaj do main'a kolejne pytanie zadaj pytanie o to co chciałby obliczyć. W zależności od WCZEŚNIEJ WYBRANEJ TABELI użytkownik powinien mieć możliwość obliczenia:
    - kursu średniego (oblicz średnią kursu)
    - odchyleń maksymalnych (ile wynosiła różnica między min a max)
    - maximów i minimów (wypisz ile wynosiły)

UWAGA! (jeśli tabela posiada pole MID, to obliczmay średnią MID), jeśli posiada pole ASK, to robimy to dla tego pola, jeśli posiada BID, to robimy dla pola BID.
UWAGA! (usuńmy z opcji podawanie tabeli B - przysparza sporo problemów i ciężko jest wydobyć z niej jakiekolwiek dane)

- PODPOWIEDŹ: Trzeba zmodyfikować wygląd klasy z XML (Rate). 
- PODPOWIEDŹ: Wystarczy dopisać brakujące pole (Mid).

3. Wykrywanie braku danych. 
Spróbuj obsłużyć komunikat o braku rekordów w tabeli. Wykryj taką informację (np. url: http://api.nbp.pl/api/exchangerates/rates/C/USD/2018-01-01/2018-01-01/?format=XML )
i wypisz użytkownikowi komunikat o braku danych. 

Propozycje:
 - przechwycić exception
 - sprawdzić format danych
 - sprawdzić treść metodą ładującą zawartość

4. Pobieranie z szerszych zakresów. 

W API istnieje możliwość pobrania maksymalnie 93 dni (niepotwierdzone info, ktoś w sali to zauważył). 

Stwórz mechanizm który po pobraniu dat od użytkownika dokonuje ładowania "chunków" czyli partii danych.
Zakładamy że do pobrania jest data od 2018-01-01 do 2018-12-31, co daje nam łącznie 364 dni.

Żeby pobrać wszystkie dane, trzeba pobrać je najpierw:
- od 2018-01-01 do 2018-04-03 (93 dni)
- od 2018-04-03 do 2018-07-05 (93 dni)
- od 2018-07-05 do 2018-10-06 (93 dni)
- od 2018-10-06 do 2018-12-31 (86 dni)

Po pobraniu danych skompletuj je do jednej kolekcji (setu rates), którego kryterium porównania to identyfikator NO.

Identyfikator "No" to wartość pola w klasie Rate. Jest to indywidualny klucz kursu. 
Nadpisz metodę equals i hashcode tak, aby umieścić w secie tylko unikane rekordy. Zweryfikuj, że zebrane zostało dokładnie tyle rekordów, o ile poprosił użytkownik.

Żeby zweryfikować ilość rekordów oblicz (Period/Duration) ile jest dni różnicy między podanymi datami.
