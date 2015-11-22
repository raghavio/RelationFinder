:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).

:- http_handler('/query', handle_query, []).

server(Port) :-
    http_server(http_dispatch, [port(Port)]).

handle_query(_Request) :-
    format('Content-type: application/json~n~n'),
    format('{"Hello": "World!"}').
