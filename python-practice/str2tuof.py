def normalize(name):
    name = name.lower()
    name = name.replace(name[:1], name[:1].upper(),1)
    return name


L1 = ['adam', 'LISA', 'barT', 'AA']
L2 = list(map(normalize, L1))
print(L2)