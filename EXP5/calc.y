%{
#include <stdio.h>
#include <stdlib.h>

int flag = 0;
%}

%token NUMBER

%left '+' '-'
%left '*' '/' '%'

%%

ArithmeticExpression: E {
    printf("\nResult = %d\n", $1);
    return 0;
};

E: E '+' E { $$ = $1 + $3; }
 | E '-' E { $$ = $1 - $3; }
 | E '*' E { $$ = $1 * $3; }
 | E '/' E { $$ = $1 / $3; }
 | E '%' E { $$ = $1 % $3; }
 | '(' E ')' { $$ = $2; }
 | NUMBER { $$ = $1; }
;

%%

int main()
{
    printf("\nEnter Arithmetic Expression:\n");
    yyparse();

    if(flag == 0)
        printf("\nValid Expression\n");
}

int yyerror()
{
    printf("\nInvalid Expression\n");
    flag = 1;
    return 0;
}