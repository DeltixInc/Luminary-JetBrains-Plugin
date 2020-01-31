grammar Luminary;

protocol
    :   namespace import_or_option* typeDefinition* EOF
    ;

namespace
    :   NAMESPACE  qualifiedName SEMICOLON
    ;

import_or_option
    :   importStatement
    |   option
    ;

importStatement
    :   IMPORT qualifiedName PERIOD IDENTIFIER SEMICOLON                # ImportType
    |   IMPORT qualifiedName PERIOD ASTERISK SEMICOLON                  # ImportEverything
    |   IMPORT qualifiedName PERIOD IDENTIFIER AS IDENTIFIER SEMICOLON  # ImportTypeWithAlias
    ;

option
    :   OPTION IDENTIFIER EQUALS_SIGN constantValue SEMICOLON
    ;

qualifiedName
    :   IDENTIFIER ( PERIOD IDENTIFIER )*
    ;

typeDefinition
    :   enumerationDefinition
    |   classDefinition
    |   interfaceDefinition
    |   decoratorDefinition
    ;

// Definition of Decorator

decoratorDefinition
    :   decorators? DECORATOR IDENTIFIER OPENING_CURLY_BRACKET decoratorEntry* CLOSING_CURLY_BRACKET
    ;

decoratorEntry
    :   decoratorProperty
    |   typeDefinition
    ;

decoratorProperty
    :   decorators? type IDENTIFIER (SEMICOLON | ( EQUALS_SIGN constantValue ) SEMICOLON)
    ;

// Definition of Interface

interfaceDefinition
    :   decorators? INTERFACE IDENTIFIER ( COLON supertypeList )? OPENING_CURLY_BRACKET interfaceEntry* CLOSING_CURLY_BRACKET
    ;

supertypeList
    :   type ( COMMA supertypeList )?
    ;

interfaceEntry
    :   interfaceProperty
    |   interfaceMethod
    |   typeDefinition
    ;

interfaceProperty
    :   decorators? OVERRIDE? type IDENTIFIER GET SET? SEMICOLON
    ;

interfaceMethod
    :   decorators? returnType IDENTIFIER OPENING_PARENTHESIS formalParameters? CLOSING_PARENTHESIS SEMICOLON
    ;

formalParameters
    :   formalFixedParameter ( COMMA formalFixedParameter)* ( COMMA formalArrayParameter )?
    |   formalArrayParameter
    ;

formalFixedParameter
    :   decorators? type IDENTIFIER
    ;

formalArrayParameter
    :   decorators? type ELLIPSIS IDENTIFIER
    ;

// Definition of Class

classDefinition
    :   decorators? FINAL? CLASS IDENTIFIER ( COLON supertypeList )? OPENING_CURLY_BRACKET classEntry* CLOSING_CURLY_BRACKET
    ;

classEntry
    :   classProperty
    |   typeDefinition
    |   constantDefinition
    ;

classProperty
    :   decorators? FINAL? OVERRIDE? classPropertyDecl SEMICOLON
    ;

classPropertyDecl
    :   type IDENTIFIER
    |   type IDENTIFIER equalConstant;

equalConstant
    :   EQUALS_SIGN constantValue
    ;

constantDefinition
    :   decorators? CONST type IDENTIFIER EQUALS_SIGN constantValue SEMICOLON
    ;

// Definition of Enumeration

enumerationDefinition
    :   decorators? ENUM IDENTIFIER ( COLON IDENTIFIER )? OPENING_CURLY_BRACKET enumerationMember+ CLOSING_CURLY_BRACKET
    ;

enumerationMember
    :   decorators? enumerationIdentifier EQUALS_SIGN constantValue SEMICOLON
    ;

enumerationIdentifier
    :   IDENTIFIER
    ;

// Type

returnType
    :   VOID
    |   type
    ;

type
    :   complexType
//    |   builtinType
    ;

//builtinType
//    :   SIMPLE_TYPE
//    |   list_type
//    ;

// Decorators

decorators
    :  ( OPENING_SQUARE_BRACKET decorator ( COMMA decorator )* CLOSING_SQUARE_BRACKET )+
    ;

decorator
    :   normalDecorator
    |   markerDecorator
    |   singleElementDecorator
    ;

normalDecorator
    :   qualifiedName OPENING_PARENTHESIS keyValuePair ( COMMA keyValuePair )* CLOSING_PARENTHESIS
    |   qualifiedName OPENING_PARENTHESIS CLOSING_PARENTHESIS
    ;

markerDecorator
    :   qualifiedName
    ;

singleElementDecorator
    :   qualifiedName OPENING_PARENTHESIS constantValue CLOSING_PARENTHESIS
    ;

keyValuePair
    :   IDENTIFIER EQUALS_SIGN constantValue
    ;

constantValue
    :   STRING_LITERAL                                                      # String
    |   CHAR_LITERAL                                                        # Char
    |   INTEGRAL_LITERAL                                                    # Integer
    |   BINARY_FLOATING_POINT_LITERAL                                       # Float
    |   DECIMAL_FLOATING_POINT_LITERAL                                      # Decimal
    |   qualifiedName                                                       # MemberReference
    |   TYPEOF OPENING_PARENTHESIS type CLOSING_PARENTHESIS                 # TypeReference
    |   TRUE                                                                # True
    |   FALSE                                                               # False
    |   NULL                                                                # Null
    |   OPENING_CURLY_BRACKET constantValueList? CLOSING_CURLY_BRACKET      # Array
    ;

constantValueList
    :   constantValue ( COMMA constantValue )*
    ;

primary
    :   IDENTIFIER
    |   constantValue
    |   qualifiedName
    ;

//list_type
//    :  LIST_TYPE LESS_THAN_SIGN ( SIMPLE_TYPE | list_type | qualifiedName | IDENTIFIER | complexType) GREATER_THAN_SIGN QUESTION_MARK?;

complexType
    : qualifiedName QUESTION_MARK?
    | IDENTIFIER LESS_THAN_SIGN type ( COMMA type )* GREATER_THAN_SIGN QUESTION_MARK?
    ;

ASTERISK:                               '*';
CLOSING_CURLY_BRACKET:                  '}';
CLOSING_PARENTHESIS:                    ')';
CLOSING_SQUARE_BRACKET:                 ']';
COLON:                                  ':';
COMMA:                                  ',';
EQUALS_SIGN:                            '=';
GREATER_THAN_SIGN:                      '>';
LESS_THAN_SIGN:                         '<';
OPENING_CURLY_BRACKET:                  '{';
OPENING_PARENTHESIS:                    '(';
OPENING_SQUARE_BRACKET:                 '[';
ELLIPSIS:                               '...';
PERIOD:                                 '.';
QUESTION_MARK:                          '?';
SEMICOLON:                              ';';

// Keywords

AS:                                     'as';
CLASS:                                  'class';
CONST:                                  'const';
DECORATOR:                              'decorator';
ENUM:                                   'enum';
FALSE:                                  'false';
GET:                                    'get';
IMPORT:                                 'import';
INTERFACE:                              'interface';
NAMESPACE:                              'namespace';
NULL:                                   'null';
OPTION:                                 'option';
OVERRIDE:                               'override';
SET:                                    'set';
TRUE:                                   'true';
TYPEOF:                                 'typeof';
VOID:                                   'void';
FINAL:                                  'final';

// types
//SIMPLE_TYPE:                            (INT_TYPE | BOOLEAN_TYPE | FLOAT_TYPE | TEXT_TYPE |
//                                        BINARY_TYPE | TIMESTAMP_TYPE | DURATION_TYPE | DATE_TYPE |
//                                        TIME_TYPE | DECIMAL_TYPE | TYPE_TYPE | UUID_TYPE)'?'?;

//INT_TYPE:                               'U'?'Int'('8'|'16'|'32'|'64');
//BOOLEAN_TYPE:                           'Boolean';
//FLOAT_TYPE:                             'Float'('32'|'64');
//TEXT_TYPE:                              'Text';
//BINARY_TYPE:                            'Data';
//TIMESTAMP_TYPE:                         'Timestamp';
//DURATION_TYPE:                          'Duration';
//DATE_TYPE:                              'Date';
//TIME_TYPE:                              'Time';
//DECIMAL_TYPE:                           'Decimal';
//TYPE_TYPE:                              'Type';
//UUID_TYPE:                              'UUID';
//LIST_TYPE:                              'List';

// Literals

STRING_LITERAL:                         '"' CHARACTERS? '"';

CHAR_LITERAL:                           '\'' CHARACTER '\'';



INTEGRAL_LITERAL:                       '-'? INTEGRAL_LITERAL_DIGITS ( 'i64' | 'i32'? | 'i16' | 'i8' ) |
                                        INTEGRAL_LITERAL_DIGITS ( 'u64' | 'u32' | 'u16' | 'u8' );

BINARY_FLOATING_POINT_LITERAL:          '-'? DEC_DIGITS PERIOD DEC_DIGITS? EXPONENT_PART? '-'* FLOATING_TYPE_SUFFIX? |
                                        '-'? PERIOD DEC_DIGITS EXPONENT_PART? '-'* FLOATING_TYPE_SUFFIX? |
                                        '-'? DEC_DIGITS EXPONENT_PART '-'* FLOATING_TYPE_SUFFIX? |
                                        '-'? DEC_DIGITS '-'* FLOATING_TYPE_SUFFIX;

DECIMAL_FLOATING_POINT_LITERAL:         '-'? DEC_DIGITS PERIOD DEC_DIGITS? EXPONENT_PART? '-'* DECIMAL_TYPE_SUFFIX |
                                        '-'? PERIOD DEC_DIGITS EXPONENT_PART? '-'* DECIMAL_TYPE_SUFFIX |
                                        '-'? DEC_DIGITS EXPONENT_PART '-'* DECIMAL_TYPE_SUFFIX |
                                        '-'? DEC_DIGITS '-'* DECIMAL_TYPE_SUFFIX;

// Identifier

IDENTIFIER:                             [a-zA-Z] [_a-zA-Z0-9]*;

// Fragments

fragment EXPONENT_PART:                 ('e'|'E')? DEC_INTEGER;
fragment FLOATING_TYPE_SUFFIX:          ('f64' | 'f32');
fragment DECIMAL_TYPE_SUFFIX:           ('d64');

fragment INTEGRAL_LITERAL_DIGITS:       HEX_INTEGER | DEC_INTEGER | OCT_INTEGER | BIN_INTEGER;
fragment DEC_DIGITS:                    [0-9][0-9_]*;
fragment HEX_INTEGER:                   '0x' [0-9a-fA-F_]+;
fragment DEC_INTEGER:                   ('0' | [1-9][0-9_]*);
fragment OCT_INTEGER:                   '0o' [0-7_]+;
fragment BIN_INTEGER:                   '0b' [0-1_]+;
fragment CHARACTERS:                    (~[\\\r\n"] | '\\' ['"?abfnrtv\\])*;
fragment CHARACTER:                     (~[\\\r\n'] | '\\' ['"?abfnrtv\\]);

// Whitespaces and Comments

WHITESPACE:                             [ \t]+ -> channel(HIDDEN);
NEW_LINE:                               ('\r' '\n' | '\n') -> channel(HIDDEN);
DOCUMENTATION_COMMENTS:                 '///' ~[\r\n]* -> channel(HIDDEN);
SINGLE_LINE_COMMENT:                    '//' .*? ('\n'|EOF) -> channel(HIDDEN);
MULTI_LINE_COMMENT:                     '/*' .*? '*/' -> channel(HIDDEN);

/** "catch all" rule for any char not matche in a token rule of your
 *  grammar. Lexers in Intellij must return all tokens good and bad.
 *  There must be a token to cover all characters, which makes sense, for
 *  an IDE. The parser however should not see these bad tokens because
 *  it just confuses the issue. Hence, the hidden channel.
 */
ERRCHAR
	:	.	-> channel(HIDDEN)
	;
