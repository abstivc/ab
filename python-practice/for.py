def findMinAndMax(L):
    max = None
    min = None
    if L == []:
        return (min, min)
    else:
        for value in L:
            if (max == None and min == None):
                max = value
                min = value
            if (value > max):
                max = value
            if (value < min):
                min = value
        return (min, max)


if findMinAndMax([]) != (None, None):
    print('测试失败1!')
elif findMinAndMax([7]) != (7, 7):
    print('测试失败2!')
elif findMinAndMax([7, 1]) != (1, 7):
    print('测试失败3!')
elif findMinAndMax([7, 1, 3, 9, 5]) != (1, 9):
    print('测试失败4!')
else:
    print('测试成功!')
