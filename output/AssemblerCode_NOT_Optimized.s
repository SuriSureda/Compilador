.global _start
.text
.data
T0: .quad 0
vx_0: .quad 0
T1: .quad 0
vy_0: .quad 0
T2: .quad 0
vz_0: .quad 0
T3: .quad 0
T4: .quad 0
T5: .quad 0
T6: .quad 0
T7: .quad 0
T8: .quad 0
T9: .quad 0
format: .asciz "%d"
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

#T3 = vx_0 add vy_0
mov vx_0, %rax
mov vy_0, %rdi
add %rax, %rdi
mov %rdi, T3

#vz_0 = T3
mov T3, %rdi
mov %rdi, vz_0

#T4 = vz_0 prod vz_0
mov vz_0, %rax
mov vz_0, %rdi
imul %rax, %rdi
mov %rdi, T4

#vz_0 = T4
mov T4, %rdi
mov %rdi, vz_0

#T5 = true
movl $1, T5

#if_EQ: T5,true goto LABEL_0
mov T5, %edx
mov $1, %ecx
cmp %ecx, %edx
je LABEL_0

#go_to LABEL_1
jmp LABEL_1

#LABEL_0:skip
LABEL_0:

#T6 = 1
mov $1, %rdi
mov %rdi, T6

#T7 = vx_0 add T6
mov vx_0, %rax
mov T6, %rdi
add %rax, %rdi
mov %rdi, T7

#vx_0 = T7
mov T7, %rdi
mov %rdi, vx_0

#go_to LABEL_2
jmp LABEL_2

#LABEL_1:skip
LABEL_1:

#T8 = 1
mov $1, %rdi
mov %rdi, T8

#T9 = vx_0 sub T8
mov vx_0, %rax
mov T8, %rdi
sub %rax, %rdi
mov %rdi, T9

#vx_0 = T9
mov T9, %rdi
mov %rdi, vx_0

#LABEL_2:skip
LABEL_2:

#exit
mov $60, %rax
xor %rdi,%rdi
syscall
