# Bracket-expansion
String parsing and multi bracket expansion  
Java implementation

### 设计的形式化说明
* 表达式 $\rightarrow$ 空白项 [加减 空白项] 项 空白项 | 表达式 加减 空白项 项 空白项
* 项 $\rightarrow$ [加减 空白项] 因子 | 项 空白项 '\*' 空白项 因子
* 因子 $\rightarrow$​ 变量因子 | 常数因子 | 表达式因子
* 变量因子 $\rightarrow$ 幂函数 | 三角函数 | 自定义函数**调用** | 求和函数
* 常数因子 $\rightarrow$​ 带符号的整数
* 表达式因子 $\rightarrow$​ '(' 表达式 ')' [空白项 指数]
* 幂函数 $\rightarrow$ (函数自变量|'i') [空白项 指数]
* 三角函数 $\rightarrow$ 'sin' 空白项 '(' 空白项 因子 空白项 ')' [空白项 指数] | 'cos' 空白项 '(' 空白项 因子 空白项 ')' [空白项 指数]
* 指数 $\rightarrow$ '\*\*' 空白项 ['+'] 允许前导零的整数
* 带符号的整数 $\rightarrow$ [加减] 允许前导零的整数
* 允许前导零的整数 $\rightarrow$ (0|1|2|…|9){0|1|2|…|9}
* 空白项 $\rightarrow$ {空白字符}
* 空白字符 $\rightarrow$ ` `（空格） | `\t`
* 加减 $\rightarrow$ '+' | '-'
* 自定义函数**定义** $\rightarrow$ 自定义函数名 空白项 '(' 空白项 函数自变量 空白项 [',' 空白项 函数自变量 空白项 [',' 空白项 函数自变量 空白项]] ')' 空白项 '=' 空白项 函数表达式
* 函数自变量 $\rightarrow$ 'x' | 'y' | 'z'
* 自定义函数**调用** $\rightarrow$ 自定义函数名 空白项 '(' 空白项 因子 空白项 [',' 空白项 因子 空白项 [',' 空白项 因子 空白项]] ')'
* 自定义函数名 $\rightarrow$ 'f' | 'g' | 'h'
* 求和函数 $\rightarrow$ 'sum' '(' 空白项 'i' 空白项',' 空白项 常数因子 空白项 ',' 空白项 常数因子 空白项 ',' 空白项 求和表达式 空白项 ')'
* 函数表达式 $\rightarrow$ 表达式
* 求和表达式 $\rightarrow$ 因子
