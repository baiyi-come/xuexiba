import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: `/larservice/ucenter/get/${username}/${password}`,
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/eduservice/teacher/info',
    method: 'get',
    params: { token }
  })
}


