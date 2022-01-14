.global main
.extern printf
.extern exit
.data
T0: .quad 0
vx_0: .quad 0
T1: .quad 0
vy_0: .quad 0
T2: .quad 0
vz_0: .quad 0
T3: .quad 0
T4: .asciz "VZ > VX\n"
T5: .quad 0
T6: .asciz "VZ > VY\n"
T7: .quad 0
T8: .quad 0
T9: .asciz "Inside loop :\n"
T10: .asciz "VZ : "
T11: .asciz "\n"
T12: .asciz "VX : "
T13: .asciz "\n"
T14: .quad 0
T15: .quad 0
T16: .quad 0
T17: .quad 0
T18: .asciz "Inside outside loop :\n"
T19: .asciz "VZ : "
T20: .asciz "\n"
T21: .asciz "VX : "
T22: .asciz "\n"
T23: .asciz "End\n"
format_int: .asciz "%d"
true_label : .asciz "true"
false_label : .asciz "false"
.text
CMP_EQ :
cmp %rdi, %rsi
jne CMP_EQ_NE
mov $1, %rax
ret
CMP_EQ_NE :
mov $0, %rax
ret
CMP_NE :
cmp %rdi, %rsi
je CMP_NE_E
mov $1, %rax
ret
CMP_NE_E :
mov $0, %rax
ret
CMP_GT :
cmp %rdi, %rsi
jl CMP_GT_LE
mov $1, %rax
ret
CMP_GT_LE :
mov $0, %rax
ret
CMP_GE :
cmp %rdi, %rsi
jl CMP_GE_L
mov $1, %rax
ret
CMP_GE_L :
mov $0, %rax
ret
CMP_LE :
cmp %rdi, %rsi
jg CMP_LE_G
mov $1, %rax
ret
CMP_LE_G :
mov $0, %rax
ret
CMP_LT :
cmp %rdi, %rsi
jge CMP_LT_GE
mov $1, %rax
ret
CMP_LT_GE :
mov $0, %rax
ret
print_bool :
cmp $0,%rdi
je print_false
mov $true_label, %rdi
jmp print_bool_val
print_false : mov $false_label, %rdi
print_bool_val : xor %rax, %rax
call printf
ret
main :
#T0 = 5
mov $5, %rdi
mov %rdi, T0

#vx_0 = T0
mov T0, %rdi
mov %rdi, vx_0

#T1 = 10
mov $10, %rdi
mov %rdi, T1

#vy_0 = T1
mov T1, %rdi
mov %rdi, vy_0

#T2 = 0
mov $0, %rdi
mov %rdi, T2

#vz_0 = T2
mov T2, %rdi
mov %rdi, vz_0

#T3= vz_0 GT vx_0
mov vx_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_GT
mov %rax,T3 # get return value

#if T3=true goto LABEL_0
cmp $1,T3
je LABEL_0

#go_to LABEL_1
jmp LABEL_1

#LABEL_0:skip
LABEL_0:

#output T4
mov $T4, %rdi
xor %rax, %rax
call printf

#go_to LABEL_2
jmp LABEL_2

#LABEL_1:skip
LABEL_1:

#T5= vz_0 GT vy_0
mov vy_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_GT
mov %rax,T5 # get return value

#if T5=true goto LABEL_3
cmp $1,T5
je LABEL_3

#go_to LABEL_4
jmp LABEL_4

#LABEL_3:skip
LABEL_3:

#output T6
mov $T6, %rdi
xor %rax, %rax
call printf

#go_to LABEL_5
jmp LABEL_5

#LABEL_4:skip
LABEL_4:

#T7= vx_0 LT vy_0
mov vy_0, %rdi
mov vx_0, %rsi
xor %rax, %rax #clean return value register
call CMP_LT
mov %rax,T7 # get return value

#if T7=true goto LABEL_6
cmp $1,T7
je LABEL_6

#go_to LABEL_7
jmp LABEL_7

#LABEL_6:skip
LABEL_6:

#LABEL_8:skip
LABEL_8:

#T8= vz_0 LT vx_0
mov vx_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_LT
mov %rax,T8 # get return value

#if T8=true goto LABEL_9
cmp $1,T8
je LABEL_9

#go_to LABEL_10
jmp LABEL_10

#LABEL_9:skip
LABEL_9:

#output T9
mov $T9, %rdi
xor %rax, %rax
call printf

#output T10
mov $T10, %rdi
xor %rax, %rax
call printf

#output vz_0
mov $format_int, %rdi
mov vz_0, %rsi
xor %rax, %rax
call printf

#output T11
mov $T11, %rdi
xor %rax, %rax
call printf

#output T12
mov $T12, %rdi
xor %rax, %rax
call printf

#output vx_0
mov $format_int, %rdi
mov vx_0, %rsi
xor %rax, %rax
call printf

#output T13
mov $T13, %rdi
xor %rax, %rax
call printf

#T14 = 1
mov $1, %rdi
mov %rdi, T14

#T15 = vz_0 add T14
mov vz_0, %rdi
mov T14, %rax
add %rax, %rdi
mov %rdi, T15

#T16 = 1
mov $1, %rdi
mov %rdi, T16

#T17 = T15 add T16
mov T15, %rdi
mov T16, %rax
add %rax, %rdi
mov %rdi, T17

#vz_0 = T17
mov T17, %rdi
mov %rdi, vz_0

#go_to LABEL_8
jmp LABEL_8

#LABEL_10:skip
LABEL_10:

#output T18
mov $T18, %rdi
xor %rax, %rax
call printf

#output T19
mov $T19, %rdi
xor %rax, %rax
call printf

#output vz_0
mov $format_int, %rdi
mov vz_0, %rsi
xor %rax, %rax
call printf

#output T20
mov $T20, %rdi
xor %rax, %rax
call printf

#output T21
mov $T21, %rdi
xor %rax, %rax
call printf

#output vx_0
mov $format_int, %rdi
mov vx_0, %rsi
xor %rax, %rax
call printf

#output T22
mov $T22, %rdi
xor %rax, %rax
call printf

#LABEL_5:skip
LABEL_5:

#LABEL_2:skip
LABEL_2:

#LABEL_7:skip
LABEL_7:

#output T23
mov $T23, %rdi
xor %rax, %rax
call printf

#exit
call exit
.global main
.extern printf
.extern exit
.data
T0: .quad 0
vx_0: .quad 0
T1: .quad 0
vy_0: .quad 0
T2: .quad 0
vz_0: .quad 0
T3: .quad 0
T4: .asciz "VZ > VX\n"
T5: .quad 0
T6: .asciz "VZ > VY\n"
T7: .quad 0
T8: .quad 0
T9: .asciz "Inside loop :\n"
T10: .asciz "VZ : "
T11: .asciz "\n"
T12: .asciz "VX : "
T13: .asciz "\n"
T14: .quad 0
T15: .quad 0
T16: .quad 0
T17: .quad 0
T18: .asciz "Inside outside loop :\n"
T19: .asciz "VZ : "
T20: .asciz "\n"
T21: .asciz "VX : "
T22: .asciz "\n"
T23: .asciz "End\n"
format_int: .asciz "%d"
true_label : .asciz "true"
false_label : .asciz "false"
.text
CMP_EQ :
cmp %rdi, %rsi
jne CMP_EQ_NE
mov $1, %rax
ret
CMP_EQ_NE :
mov $0, %rax
ret
CMP_NE :
cmp %rdi, %rsi
je CMP_NE_E
mov $1, %rax
ret
CMP_NE_E :
mov $0, %rax
ret
CMP_GT :
cmp %rdi, %rsi
jl CMP_GT_LE
mov $1, %rax
ret
CMP_GT_LE :
mov $0, %rax
ret
CMP_GE :
cmp %rdi, %rsi
jl CMP_GE_L
mov $1, %rax
ret
CMP_GE_L :
mov $0, %rax
ret
CMP_LE :
cmp %rdi, %rsi
jg CMP_LE_G
mov $1, %rax
ret
CMP_LE_G :
mov $0, %rax
ret
CMP_LT :
cmp %rdi, %rsi
jge CMP_LT_GE
mov $1, %rax
ret
CMP_LT_GE :
mov $0, %rax
ret
print_bool :
cmp $0,%rdi
je print_false
mov $true_label, %rdi
jmp print_bool_val
print_false : mov $false_label, %rdi
print_bool_val : xor %rax, %rax
call printf
ret
main :
#vx_0 = 5 = 5
mov $5, %rdi
mov %rdi, vx_0 = 5

#vy_0 = 10 = 10
mov $10, %rdi
mov %rdi, vy_0 = 10

#vz_0 = 0 = 0
mov $0, %rdi
mov %rdi, vz_0 = 0

#T3= vz_0 GT vx_0
mov vx_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_GT
mov %rax,T3 # get return value

#if T3=true goto LABEL_0
cmp $1,T3
je LABEL_0

#go_to LABEL_1
jmp LABEL_1

#LABEL_0:skip
LABEL_0:

#output T4
mov $T4, %rdi
xor %rax, %rax
call printf

#go_to LABEL_2
jmp LABEL_2

#LABEL_1:skip
LABEL_1:

#T5= vz_0 GT vy_0
mov vy_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_GT
mov %rax,T5 # get return value

#if T5=true goto LABEL_3
cmp $1,T5
je LABEL_3

#go_to LABEL_4
jmp LABEL_4

#LABEL_3:skip
LABEL_3:

#output T6
mov $T6, %rdi
xor %rax, %rax
call printf

#go_to LABEL_5
jmp LABEL_5

#LABEL_4:skip
LABEL_4:

#T7= vx_0 LT vy_0
mov vy_0, %rdi
mov vx_0, %rsi
xor %rax, %rax #clean return value register
call CMP_LT
mov %rax,T7 # get return value

#if T7=true goto LABEL_6
cmp $1,T7
je LABEL_6

#go_to LABEL_7
jmp LABEL_7

#LABEL_6:skip
LABEL_6:

#LABEL_8:skip
LABEL_8:

#T8= vz_0 LT vx_0
mov vx_0, %rdi
mov vz_0, %rsi
xor %rax, %rax #clean return value register
call CMP_LT
mov %rax,T8 # get return value

#if T8=true goto LABEL_9
cmp $1,T8
je LABEL_9

#go_to LABEL_10
jmp LABEL_10

#LABEL_9:skip
LABEL_9:

#output T9
mov $T9, %rdi
xor %rax, %rax
call printf

#output T10
mov $T10, %rdi
xor %rax, %rax
call printf

#output vz_0
mov $format_int, %rdi
mov vz_0, %rsi
xor %rax, %rax
call printf

#output T11
mov $T11, %rdi
xor %rax, %rax
call printf

#output T12
mov $T12, %rdi
xor %rax, %rax
call printf

#output vx_0
mov $format_int, %rdi
mov vx_0, %rsi
xor %rax, %rax
call printf

#output T13
mov $T13, %rdi
xor %rax, %rax
call printf

#T14 = 1
mov $1, %rdi
mov %rdi, T14

#T15 = vz_0 add T14
mov vz_0, %rdi
mov T14, %rax
add %rax, %rdi
mov %rdi, T15

#T16 = 1
mov $1, %rdi
mov %rdi, T16

#vz_0 = T15 + T16 = T15 add T16
mov T15, %rdi
mov T16, %rax
add %rax, %rdi
mov %rdi, vz_0 = T15 + T16

#go_to LABEL_8
jmp LABEL_8

#LABEL_10:skip
LABEL_10:

#output T18
mov $T18, %rdi
xor %rax, %rax
call printf

#output T19
mov $T19, %rdi
xor %rax, %rax
call printf

#output vz_0
mov $format_int, %rdi
mov vz_0, %rsi
xor %rax, %rax
call printf

#output T20
mov $T20, %rdi
xor %rax, %rax
call printf

#output T21
mov $T21, %rdi
xor %rax, %rax
call printf

#output vx_0
mov $format_int, %rdi
mov vx_0, %rsi
xor %rax, %rax
call printf

#output T22
mov $T22, %rdi
xor %rax, %rax
call printf

#LABEL_5:skip
LABEL_5:

#LABEL_2:skip
LABEL_2:

#LABEL_7:skip
LABEL_7:

#output T23
mov $T23, %rdi
xor %rax, %rax
call printf

#exit
call exit
