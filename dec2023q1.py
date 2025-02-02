# O(N log N)
def f(N, M, K, cows, weights):
    weights.sort()  # O(N log N)
    stacks = {0: M}  # maps w to num cows with w as top of stack

    i = -1  # index -1 means no cows yet (top weight 0)
    j = 0
    count = 0
    # O(N) since either i or j increments on each step.
    # cows maps w to num cows NOT YET IN A STACK with weight w.
    while i < N and j < N:
        wi = weights[i] if i >= 0 else 0
        wj = weights[j]
        if wi not in stacks:
            i += 1
            continue
        elif wj not in cows:
            j += 1
            continue
        elif wi + K <= wj or wi == 0:
            ni = stacks[wi]  # num of stacks with top weight wi that want promotion to wj
            nj = cows[wj]  # num of cows available at weight wj to allow this promotion to happen

            npromote = min(ni, nj)
            count += npromote

            # promote npromote stacks from wi to wj
            stacks[wi] -= npromote
            stacks[wj] = (stacks[wj] + npromote) if (wj in stacks) else npromote
            if stacks[wi] == 0:
                del stacks[wi]
                i += 1
            # update cows dict to remove newly added npromote cows
            cows[wj] -= npromote
            if cows[wj] == 0:
                del cows[wj]
                j += 1
        else:
            # lightest unused cows are too light, so go to next lightest weight
            j += 1
    return count

N, M, K = input().split()
N = int(N)
M = int(M)
K = int(K)
w = []
a = []
for i in range(N):
    next = input().split()
    w.append(int(next[0]))
    a.append(int(next[1]))

print(f(N, M, K, cows={wi: ai for wi, ai in zip(w, a)}, weights=w.copy()))